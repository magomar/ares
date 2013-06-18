package ares.application.shared.gui.components;

import ares.application.shared.gui.ComponentFactory;
import ares.application.shared.gui.WindowUtil;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.EnumSet;

/**
 * Creates and manages a standardized progress dialog. Any of the public methods on this class (including the
 * constructor) may be executed from any thread; the actual operations are queued for the event thread. <p> The dialog
 * is displayed by calling {@link #show}, hidden by calling {@link #hide}. It is not actually created until the first
 * call to {@code show()}; the constructor merely records information. Nor is it disposed by calling {@link #hide}; you
 * must explicitly call {@link #dispose}.
 */
public class ProgressMonitor {

    /**
     * Options to control the dialog's appearance. Rather than providing a plethora of constructors (like typical Swing
     * dialogs), you can pass zero or more of these options to the base constructor.
     */
    public enum Options {

        /**
         * If present, the dialog is modal.
         */
        MODAL,
        /**
         * If present, the dialog will be displayed centered on its parent. If the dialog was constructed without a
         * parent, it will be centered on the screen.
         */
        CENTER,
        /**
         * If present, the dialog will be constructed without a progress bar (typically this is used with
         * {@link #SHOW_STATUS}).
         */
        NO_PROGRESS_BAR,
        /**
         * If present, the dialog will include space for a status message below the progress bar.
         */
        SHOW_STATUS,
        /**
         * If present, the progress bar displays completion percentage; by default it shows the count.
         */
        SHOW_PERCENT_COMPLETE
    }

    //----------------------------------------------------------------------------
//  Instance Data and Constructors
//----------------------------------------------------------------------------
    private Frame owner;
    private String title;
    private String text;
    private Action action;
    private EnumSet<Options> options = EnumSet.noneOf(Options.class);
    private JDialog dialog;
    private JProgressBar progressBar;
    private JLabel status;
    private volatile Integer min;
    private volatile Integer max;
    private volatile Integer current;

    /**
     * Base constructor, allowing customization of the dialog's appearance and behavior. Note that the actual dialog is
     * constructed on the first call to {@link #show}.
     *
     * @param owner   The dialog owner; may be <code>null</code>, in which case Swing will generate a hidden owner frame.
     * @param title   Text to display in the dialog window's title area; may be <code>null</code>, in which case the title
     *                is left empty.
     * @param text    Text to display in the body of the dialog; usually gives an overall description of what's happening.
     *                May be <code>null</code>.
     * @param action  If not <code>null</code>, the dialog will display a single button that invokes this action
     *                (normally used for a cancel button).
     * @param options Zero or more options controlling the dialog's appearance and behavior.
     */
    public ProgressMonitor(JFrame owner, String title, String text,
                           Action action, Options... options) {
        this.owner = owner;
        this.title = (title != null) ? title : "";
        this.text = text;
        this.action = action;
        this.options.addAll(Arrays.asList(options));
    }

    /**
     * Convenience constructor for a dialog that does not have an action button.
     *
     * @param owner   The dialog owner; may be <code>null</code>, in which case Swing will generate a hidden owner frame.
     * @param title   Text to display in the dialog window's title area; may be <code>null</code>, in which case the title
     *                is left empty.
     * @param text    Text to display in the body of the dialog; usually gives an overall description of what's happening.
     *                May be <code>null</code>.
     * @param options Zero or more options controlling the dialog's appearance and behavior.
     */
    public ProgressMonitor(JFrame owner, String title, String text, Options... options) {
        this(owner, title, text, null, options);
    }

//----------------------------------------------------------------------------
//  Public Methods
//----------------------------------------------------------------------------

    /**
     * Displays the dialog, constructing it if necessary. The dialog initially displays in indeterminate mode, unless
     * {@link #setProgress} was called prior to this method.
     *
     * @returns The controller itself, as a convenience for construct-and-show usage (but still assigning to a
     * variable).
     */
    public ProgressMonitor show() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (dialog == null) {
                    constructDialog();
                }
                internalSetProgress();
                WindowUtil.centerAndShow(dialog, owner);
            }
        });
        return this;
    }

    /**
     * Hides the dialog, but does not dispose it; also resets progress data. This method is useful if the dialog is to
     * be reused.
     */
    public void hide() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dialog.setVisible(false);
                reset();
            }
        });
    }

    /**
     * Disposes the dialog, allowing the JVM to reclaim all resources. Any future calls to {@link #show} will have to
     * reconstruct it.
     */
    public void dispose() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dialog.dispose();
                dialog = null;
                reset();
            }
        });
    }

    /**
     * Sets the dialog's progress indicator to the given values. If the dialog was previously in indeterminate mode,
     * this will switch it to determinate mode.
     *
     * @param min     The minimum progress value.
     * @param current The current progress value; this sets the position of the dialog's indicator.
     * @param max     The maximum progress value.
     * @returns The controller itself, as a convenience for construct-and-show or set-and-show usage.
     */
    public void setProgress(int min, int current, int max) {
        this.min = Integer.valueOf(min);
        this.max = Integer.valueOf(max);
        this.current = Integer.valueOf(current);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // bozo check
                if (progressBar == null) {
                    return;
                }
                internalSetProgress();
            }
        });
    }

    /**
     * Sets the status text, if the dialog was constructed with that option.
     */
    public void setStatus(final String message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // bozo check
                if (status == null) {
                    return;
                }
                status.setText(message);
            }
        });
    }

    /**
     * Switches the dialog to indeterminate mode.
     *
     * @returns The controller itself, as a convenience for construct-and-show or set-and-show usage.
     */
    public void clearProgress() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // bozo check
                if (progressBar == null) {
                    return;
                }
                reset();
                internalSetProgress();
            }
        });
    }

    //----------------------------------------------------------------------------
//  Internals -- all invoked on the event dispatch thread
//----------------------------------------------------------------------------
    private void constructDialog() {
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        if (text != null) {
            JLabel label = new JLabel(text);
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
            panel.add(label);
            contentPane.add(panel, BorderLayout.NORTH);
        }
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        contentPane.add(centerPanel, BorderLayout.CENTER);

        if (!options.contains(Options.NO_PROGRESS_BAR)) {
            progressBar = new JProgressBar();
            progressBar.setMinimumSize(new Dimension(150, 30));
            progressBar.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            centerPanel.add(progressBar);
        }

        if (options.contains(Options.SHOW_STATUS)) {
            status = new JLabel();
            status.setPreferredSize(new Dimension(300, 36));
            status.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            centerPanel.add(Box.createVerticalStrut(8));
            centerPanel.add(status);
        }

        if (action != null) {
            JButton button = ComponentFactory.button(action);
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
            panel.add(button);
            contentPane.add(panel, BorderLayout.SOUTH);
        }

        dialog = new JDialog(owner, title, options.contains(Options.MODAL));
        dialog.setContentPane(contentPane);
        dialog.pack();
    }

    private void reset() {
        min = null;
        max = null;
        current = null;
    }

    private void internalSetProgress() {
        if ((min == null) || (max == null)) {
            progressBar.setIndeterminate(true);
            progressBar.setStringPainted(false);
        } else {
            progressBar.setIndeterminate(false);
            progressBar.setStringPainted(options.contains(Options.SHOW_PERCENT_COMPLETE));
            progressBar.setMinimum(min.intValue());
            progressBar.setMaximum(max.intValue());
            if (current != null) {
                progressBar.setValue(current.intValue());
            }
        }
    }
}

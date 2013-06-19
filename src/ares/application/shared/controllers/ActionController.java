package ares.application.shared.controllers;

import ares.application.shared.commands.ActionGroup;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface ActionController {
    ActionGroup getActionGroup();
}

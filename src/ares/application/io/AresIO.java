package ares.application.io;

import ares.io.AresFileType;
import ares.io.FileIO;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class AresIO {

    public static final FileIO<AresFileType> ARES_IO = new FileIO<>(AresFileType.class, AresFileType.JAXB_CONTEXT_PATH, AresFileType.JAXB_NAMESPACE);
}

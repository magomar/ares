package ares.io;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class AresIO {

    public static final FileIO<AresFileType> ARES_IO = new FileIO<>(AresFileType.class, AresFileType.JAXB_CONTEXT_PATH, AresFileType.JAXB_NAMESPACE);
}

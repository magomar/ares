package ares.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * This is the main entry class to access the File Input and Ouput services offered by this API
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class FileIO<E extends Enum<E> & FileType> {

    private String namespace;
    private Unmarshaller unmarshaller;
    private Marshaller marshaller;
    private Path basePath;

    public FileIO(Class<E> fileTypeEnumClass, String jaxbContextPath, String namespace) {
        this(System.getProperty("user.dir"), fileTypeEnumClass, jaxbContextPath, namespace);
    }

    public FileIO(String path, Class<E> fileTypeEnumClass, String jaxbContextPath, String namespace) {
        this(FileSystems.getDefault().getPath(path), fileTypeEnumClass, jaxbContextPath, namespace);
    }

    public FileIO(Path path, Class<E> fileTypeEnumClass, String jaxbContextPath, String namespace) {
        this.namespace = namespace;
        if (path.isAbsolute()) {
            this.basePath = path;
        } else {
            this.basePath = FileSystems.getDefault().getPath(System.getProperty("user.dir"), path.toString());
        }
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(jaxbContextPath);
            unmarshaller = jaxbContext.createUnmarshaller();
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        } catch (JAXBException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Path getBasePath() {
        return basePath;
    }

    public Path getRelativePath(File file) {
        return file.toPath().relativize(basePath);
    }

    public Path getAbsolutePath(String relativePath) {
        return FileSystems.getDefault().getPath(basePath.toString(), relativePath);
    }

    public Path getAbsolutePath(String... relativePaths) {
        return FileSystems.getDefault().getPath(basePath.toString(), relativePaths);
    }

    public File getFile(String relativePath, String fileName) {
        return getAbsolutePath(relativePath, fileName).toFile();
    }

    
//    public File getFile(Path relativePath, String fileName) {
//        return getAbsolutePath(relativePath.toString(), fileName).toFile();
//    }

    /**
     * Unmarshalls XML element from file into java object
     *
     * @param xmlFile the XML file to be unmarshalled
     * @return
     */
    public Object unmarshall(File xmlFile) {
        Object object = null;
//        try {
//            StreamSource source = new StreamSource(xmlFile);
//            object = unmarshaller.unmarshal(source);
//        } catch (JAXBException ex) {
//            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
//            object = unmarshallAddingNamespace(xmlFile);
//        }
        object = unmarshallAddingNamespace(xmlFile);
        return object;
    }

    /**
     * Unmarshalls XML element from file into java object using a SAX filter to insert namespace in case it is missing
     *
     * @param xmlFile the XML file to be unmarshalled
     * @return
     */
    private Object unmarshallAddingNamespace(File xmlFile) {
        Object object = null;
        try {
            XMLReader reader;
            try {
                reader = XMLReaderFactory.createXMLReader();
                NamespaceFilter inFilter = new NamespaceFilter(namespace, true);
                inFilter.setParent(reader);
                InputSource is;
                try {
                    is = new InputSource(new FileInputStream(xmlFile));
                    SAXSource source = new SAXSource(inFilter, is);
                    object = unmarshaller.unmarshal(source);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SAXException ex) {
                Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (JAXBException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return object;
    }

    /**
     * Marshalls Java object into XML file
     *
     * @param object object to be marshalled
     * @param xmlFile file to save the marshalled object
     * @return
     */
    public File marshall(Object object, File xmlFile) {
        try {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            try (FileOutputStream fos = new FileOutputStream(xmlFile)) {
                marshaller.marshal(object, fos);
                return xmlFile;
            } catch (IOException ex) {
                Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (JAXBException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Marshalls Java object in a zipped XMl file
     *
     * @param object object to be marshalled
     * @param file non zip file to save the marshalled object
     * @return
     */
    public File marshallZipped(Object object, File file) {
        File zipFile = new File(file.getAbsolutePath() + ".zip");
        try {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            try {
                FileOutputStream fos = new FileOutputStream(zipFile);
                try (ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos))) {
                    ZipEntry ze = new ZipEntry(file.getName());
                    zos.putNextEntry(ze);
                    marshaller.marshal(object, zos);
                    return zipFile;
                } catch (IOException ex) {
                    Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JAXBException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Marshalls Java object in a gzipped XMl file
     *
     * @param object object to be marshalled
     * @param file file to save the marshalled object
     * @return
     */
    public File marshallGzipped(Object object, File file) {
        File gzFile = new File(file.getAbsolutePath() + ".gz");
        try {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            try {
                FileOutputStream fos = new FileOutputStream(gzFile);
                try {
                    GZIPOutputStream gz = new GZIPOutputStream(fos);
                    marshaller.marshal(object, gz);
                    return gzFile;
                } catch (IOException ex) {
                    Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (JAXBException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

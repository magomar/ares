package ares.io;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
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
import javax.imageio.ImageIO;
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
public class FileIO<T extends Enum<T> & FileType> {

    private static final Logger LOG = Logger.getLogger(FileIO.class.getName());
    private String namespace;
    private Unmarshaller unmarshaller;
    private Marshaller marshaller;
    private Path basePath;
    private ObjectMapper mapper;

    public FileIO(Class<T> fileTypeEnumClass, String jaxbContextPath, String namespace) {
        this(System.getProperty("user.dir"), fileTypeEnumClass, jaxbContextPath, namespace);
    }

    public FileIO(String path, Class<T> fileTypeEnumClass, String jaxbContextPath, String namespace) {
        this(FileSystems.getDefault().getPath(path), fileTypeEnumClass, jaxbContextPath, namespace);
    }
// TODO fileTypeEnumClass is not used, can we remove this?
    public FileIO(Path path, Class<T> fileTypeEnumClass, String jaxbContextPath, String namespace) {
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
            LOG.log(Level.SEVERE, null, ex);
        }
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    public final Path getBasePath() {
        return basePath;
    }

    public final Path getRelativePath(File file) {
        return file.toPath().relativize(basePath);
    }

    public final Path getAbsolutePath(String relativePath) {
        return FileSystems.getDefault().getPath(basePath.toString(), relativePath);
    }

    public final Path getAbsolutePath(String... relativePaths) {
        return FileSystems.getDefault().getPath(basePath.toString(), relativePaths);
    }

    public final File getFile(String relativePath, String fileName) {
        return getAbsolutePath(relativePath, fileName).toFile();
    }

    /**
     * Unmarshalls XML element from file into java object
     *
     * @param xmlFile the XML file to be unmarshalled
     * @return
     */
    public <T> T unmarshallJson(File file, Class<T> c) {
        T object = null;
        try {
            object = mapper.readValue(file, c);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return object;
    }

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
//            LOG.log(Level.SEVERE, null, ex);
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
                    LOG.log(Level.SEVERE, null, ex);
                }
            } catch (SAXException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        } catch (JAXBException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return object;
    }

    /**
     * Marshalls Java object into JSON file
     *
     * @param object object to be marshalled
     * @param file file to save the marshalled object
     * @return
     */
    public File marshallJson(Object object, File file) {
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        try {
            writer.writeValue(file, object);
            return file;
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return null;
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
                LOG.log(Level.SEVERE, null, ex);
            }
        } catch (JAXBException ex) {
            LOG.log(Level.SEVERE, null, ex);
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
                    LOG.log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }

        } catch (JAXBException ex) {
            LOG.log(Level.SEVERE, null, ex);
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
                    LOG.log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }

        } catch (JAXBException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
     /**
     * Loads an image from file
     *
     * @param file
     * @return the image
     */
    public static BufferedImage loadImage(File file) {
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Error loading " + file.getAbsolutePath(), ex);
        }
//        Image i = new ImageIcon(file.getPath()).getImage();
//        BufferedImage bi = new BufferedImage(i.getWidth(null),i.getHeight(null),BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g2 = bi.createGraphics();
//        g2.drawImage(i, 0, 0, null);
        return bi;
    }

    /**
     * Saves image into file
     *
     * @param image
     * @param file
     */
    public static void saveImage(RenderedImage image, File file) {
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
}

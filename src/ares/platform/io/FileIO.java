package ares.platform.io;

import ares.data.wrappers.equipment.EquipmentDB;
import ares.platform.scenario.Scenario;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.sax.SAXSource;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class FileIO {

    private static final Logger LOG = Logger.getLogger(FileIO.class.getName());

    private FileIO() {
    }

    public static Scenario loadScenario(File file, EquipmentDB equipmentDB) {
        ares.data.wrappers.scenario.Scenario scenario = FileIO.unmarshallJson(file, ares.data.wrappers.scenario.Scenario.class);
        return new Scenario(scenario, equipmentDB);
    }

    public static Scenario loadScenario(File file) {
        File equipmentFile = ResourcePath.EQUIPMENT.getFile("ToawEquipment" + AresFileType.EQUIPMENT.getFileExtension());
        EquipmentDB eqp = FileIO.unmarshallJson(equipmentFile, EquipmentDB.class);
        ares.data.wrappers.scenario.Scenario scenario = FileIO.unmarshallJson(file, ares.data.wrappers.scenario.Scenario.class);
        return new Scenario(scenario, eqp);
    }

    public static List<File> listFiles(File directory, FilenameFilter filter, boolean recurse) {
        List<File> files = new ArrayList<>();
        File[] entries = directory.listFiles();
        for (File entry : entries) {
            if (filter == null || filter.accept(directory, entry.getName())) {
                files.add(entry);
            }
            if (recurse && entry.isDirectory()) {
                files.addAll(listFiles(entry, filter, recurse));
            }
        }

        return files;
    }

    /**
     * Unmarshalls Json element of type {@code c} from the {@code file}.
     *
     * @param c    the class of object to be unmarshalled
     * @param file the Json file containing the marshalled object
     * @return the object of type {@code T} from the {@code file}
     */
    public static <T> T unmarshallJson(File file, Class<T> c) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        T object = null;
        try {
            object = mapper.readValue(file, c);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return object;
    }

    /**
     * Marshalls Java object into a Json file
     *
     * @param object object to be marshalled
     * @param file   file to save the marshalled object
     * @return
     */
    public static File marshallJson(Object object, File file) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
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
            LOG.log(Level.SEVERE, "Error loading " + file.getPath(), ex);
        }
        return bi;
    }

    /**
     * Saves image into file
     *
     * @param image
     * @param file
     */
    public static void saveImage(RenderedImage image, File file, String imageFormat) {
        try {
            ImageIO.write(image, imageFormat, file);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }


    /**
     * Unmarshalls XML element from file into java object
     *
     * @param file         the XML file to be unmarshalled
     * @param unmarshaller an unmarshalled
     * @param namespace    the namespace of the XML elements in the file
     * @return the object unmarshalled from the {@code file}
     */
    public static Object unmarshallXML(File file, Unmarshaller unmarshaller, String namespace) {
        Object object;
//        try {
//            StreamSource source = new StreamSource(file);
//            object = unmarshaller.unmarshal(source);
//        } catch (JAXBException ex) {
//            LOG.log(Level.SEVERE, null, ex);
//            object = unmarshallAddingNamespace(file);
//        }
        object = unmarshallXMLAddingNamespace(file, unmarshaller, namespace);
        return object;
    }

    /**
     * Unmarshalls XML element from file into java object using a SAX filter to insert namespace in case it is missing
     *
     * @param file         the XML file to be unmarshalled
     * @param unmarshaller an unmarshalled
     * @param namespace    the namespace of the XML elements in the file
     * @return the object unmarshalled from the {@code file}
     */
    private static Object unmarshallXMLAddingNamespace(File file, Unmarshaller unmarshaller, String namespace) {
        Object object = null;
        try {
            XMLReader reader;
            try {
                reader = XMLReaderFactory.createXMLReader();
                NamespaceFilter inFilter = new NamespaceFilter(namespace, true);
                inFilter.setParent(reader);
                InputSource is;
                try {
                    is = new InputSource(new FileInputStream(file));
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
     * Marshalls Java object into XML file
     *
     * @param object     object to be marshalled
     * @param file       file to save the marshalled object
     * @param marshaller a marshaller
     * @return the XML file
     */
    public static File marshallXML(Object object, File file, Marshaller marshaller) {
        try {
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                marshaller.marshal(object, fos);
                return file;
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
     * @param object     object to be marshalled
     * @param file       non zip file to save the marshalled object
     * @param marshaller a marshaller
     * @return the ZIP file
     */
    public static File marshallZipped(Object object, File file, Marshaller marshaller) {
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
     * @param object     object to be marshalled
     * @param file       file to save the marshalled object
     * @param marshaller a marshaller
     * @return the Gzip file
     */
    public static File marshallGzipped(Object object, File file, Marshaller marshaller) {
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


}

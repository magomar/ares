package ares.scenario.assets;

import ares.data.jaxb.EquipmentDB;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class AssetTypes {

    private final Map<String, AssetType> assetTypes;

    public AssetTypes(EquipmentDB eqpDB) {
       assetTypes = new HashMap<>();
        for (EquipmentDB.EquipmentCategory ec : eqpDB.getEquipmentCategory()) {
            for (EquipmentDB.EquipmentCategory.Item it : ec.getItem()) {
                assetTypes.put(it.getName(), new AssetType(it));
            }
        }
    }

    public AssetType getAssetType(String name) {
        return assetTypes.get(name);
    }

   
}

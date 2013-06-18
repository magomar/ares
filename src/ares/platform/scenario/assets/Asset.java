package ares.platform.scenario.assets;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public final class Asset {

    private int number;
    private int max;
    private AssetType type;

    public Asset(ares.data.wrappers.scenario.Unit.Equipment e, AssetTypes assetTypes) {
        type = assetTypes.getAssetType(e.getName());
        number = e.getNumber();
        max = e.getMax();
    }

    public AssetType getType() {
        return type;
    }

    public int getMax() {
        return max;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return type.getName() + " (" + number + '/' + max + ')';
    }
}

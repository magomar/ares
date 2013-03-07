/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ares.scenario.assets;

import ares.data.jaxb.EquipmentDB.EquipmentCategory.Item;
import ares.data.jaxb.Trait;
import java.util.EnumSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public final class AssetType {
    private static final Logger LOG = Logger.getLogger(AssetType.class.getName());

    private int id;
    private String name;
    private String country;
    private int icon;
    private int at;
    private int ap;
    private int aal;
    private int aah;
    private int df;
    private int artyRange;
    private int earlyRange;
    private int samRange;
    private int nuke;
    private int volume;
    private int weight;
    private int shellWeight;
    private int armor;
    /**
     * Speed in meters per minute
     */
    private int speed;
    private Set<AssetTrait> traits;

    public AssetType(Item i) {
        traits = EnumSet.noneOf(AssetTrait.class);
        for (Trait trait : i.getTrait()) {
            traits.add(AssetTrait.valueOf(trait.name()));
        }

        id = i.getId();
        name = i.getName();
        country = i.getCountry();
        icon = i.getIcon();
        armor = i.getArmor();
        at = i.getAT();
        ap = i.getAP();
        int aa = i.getAA();
        if (aa > 0) {
            if (traits.contains(AssetTrait.HIGH_LOW_AA) || traits.contains(AssetTrait.HIGH_ALTITUDE_AIRCRAFT)) {
                aal = aa;
                aah = aa;
            } else if (traits.contains(AssetTrait.HIGH_AA)) {
                aah = aa;
            } else {
                aal = aa;
            }
        }
        df = i.getDF();
        artyRange = i.getArtyRange();
        earlyRange = i.getEarlyRange();
        samRange = i.getSAMRange();
        nuke = i.getNuke();
        volume = i.getVolume();
        weight = i.getWeight() / 1000;
        shellWeight = i.getShellWeight();

        Set<AssetTrait> set = EnumSet.copyOf(traits);
        set.retainAll(AssetTrait.MOVEMENT);
        if (set.isEmpty()) { // this happens with SAM
            traits.add(AssetTrait.STATIC); // I assume SAM can not be moved without transport
            speed = (int) (AssetTrait.STATIC.getFactor() * 1000.0 / 60);
            LOG.log(Level.WARNING, "No movement traits, assuming it is SAM: {0}", name);
        } else if (set.size() > 1) {
            LOG.log(Level.WARNING, "Too many movement traits: {0}", name);
        } else {
            AssetTrait ast = (AssetTrait) set.toArray()[0];
            speed = (int) (ast.getFactor() * 1000.0 / 60);
        }
    }

    public int getAah() {
        return aah;
    }

    public int getAal() {
        return aal;
    }

    public int getAp() {
        return ap;
    }

    public int getArmor() {
        return armor;
    }

    public int getArtyRange() {
        return artyRange;
    }

    public int getAt() {
        return at;
    }

    public String getCountry() {
        return country;
    }

    public int getDf() {
        return df;
    }

    public int getEarlyRange() {
        return earlyRange;
    }

    public int getIcon() {
        return icon;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNuke() {
        return nuke;
    }

    public int getSamRange() {
        return samRange;
    }

    public int getShellWeight() {
        return shellWeight;
    }

    public Set<AssetTrait> getTraits() {
        return traits;
    }

    public int getVolume() {
        return volume;
    }

    public int getWeight() {
        return weight;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        return "AssetType{" + "id=" + id + ", name=" + name + ", country=" + country + ", icon=" + icon + ", at=" + at + ", ap=" + ap + ", aal=" + aal + ", aah=" + aah + ", df=" + df + ", artyRange=" + artyRange + ", earlyRange=" + earlyRange + ", samRange=" + samRange + ", nuke=" + nuke + ", volume=" + volume + ", weight=" + weight + ", shellWeight=" + shellWeight + ", armor=" + armor + ", traits=" + traits + '}';
    }
}

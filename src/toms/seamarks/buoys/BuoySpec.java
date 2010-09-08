//License: GPL. For details, see LICENSE file.
// Copyright (c) 2009 / 2010 by Werner Koenig & Malcolm Herring

package toms.seamarks.buoys;

import java.util.Map;

import javax.swing.ImageIcon;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.command.ChangePropertyCommand;
import org.openstreetmap.josm.data.osm.Node;

import toms.Messages;
import toms.dialogs.SmpDialogAction;
import toms.seamarks.SeaMark;

public class BuoySpec extends Buoy {
    public BuoySpec(SmpDialogAction dia, Node node) {
        super(dia);

        String str;
        Map<String, String> keys;
        keys = node.getKeys();
        setNode(node);

        resetMask();

        dlg.cbM01StyleOfMark.removeAllItems();
        dlg.cbM01StyleOfMark.addItem(Messages.getString("SmpDialogAction.212")); //$NON-NLS-1$
        dlg.cbM01StyleOfMark.addItem(Messages.getString("Buoy.01")); //$NON-NLS-1$
        dlg.cbM01StyleOfMark.addItem(Messages.getString("Buoy.04")); //$NON-NLS-1$
        dlg.cbM01StyleOfMark.addItem(Messages.getString("Buoy.08")); //$NON-NLS-1$
        dlg.cbM01StyleOfMark.addItem(Messages.getString("Buoy.09")); //$NON-NLS-1$
        dlg.cbM01StyleOfMark.addItem(Messages.getString("Buoy.07")); //$NON-NLS-1$
        dlg.cbM01StyleOfMark.addItem(Messages.getString("Buoy.05")); //$NON-NLS-1$
        dlg.cbM01StyleOfMark.addItem(Messages.getString("Buoy.06")); //$NON-NLS-1$
        dlg.cbM01StyleOfMark.setVisible(true);
        dlg.lM01StyleOfMark.setVisible(true);

        dlg.cbM01Kennung.removeAllItems();
        dlg.cbM01Kennung.addItem(Messages.getString("SmpDialogAction.212")); //$NON-NLS-1$
        dlg.cbM01Kennung.addItem("Fl"); //$NON-NLS-1$
        dlg.cbM01Kennung.addItem("Fl()"); //$NON-NLS-1$
        dlg.cbM01Kennung.addItem("Oc"); //$NON-NLS-1$
        dlg.cbM01Kennung.addItem("Oc()"); //$NON-NLS-1$
        dlg.cbM01Kennung.addItem("Q"); //$NON-NLS-1$
        dlg.cbM01Kennung.addItem("IQ"); //$NON-NLS-1$
        dlg.cbM01Kennung.setSelectedIndex(0);

        dlg.cM01TopMark.setEnabled(true);

        setBuoyIndex(SPECIAL_PURPOSE);
        setColour(SeaMark.YELLOW);
        setLightColour("W"); //$NON-NLS-1$
        setRegion(Main.pref.get("tomsplugin.IALA").equals("B")); //$NON-NLS-1$ //$NON-NLS-2$

        if (keys.containsKey("name")) //$NON-NLS-1$
            setName(keys.get("name")); //$NON-NLS-1$

        if (keys.containsKey("seamark:name")) //$NON-NLS-1$
            setName(keys.get("seamark:name")); //$NON-NLS-1$

        if (keys.containsKey("seamark:buoy_special_purpose:name")) //$NON-NLS-1$
            setName(keys.get("seamark:buoy_special_purpose:name")); //$NON-NLS-1$
        else if (keys.containsKey("seamark:beacon_special_purpose:name")) //$NON-NLS-1$
            setName(keys.get("seamark:beacon_special_purpose:name")); //$NON-NLS-1$
        else if (keys.containsKey("seamark:light_float:name")) //$NON-NLS-1$
            setName(keys.get("seamark:light_float:name")); //$NON-NLS-1$

        if (keys.containsKey("seamark:buoy_special_purpose:shape")) { //$NON-NLS-1$
            str = keys.get("seamark:buoy_special_purpose:shape"); //$NON-NLS-1$

            if (str.equals("pillar")) //$NON-NLS-1$
                setStyleIndex(SPEC_PILLAR);
            else if (str.equals("spar")) //$NON-NLS-1$
                setStyleIndex(SPEC_SPAR);
            else if (str.equals("sphere")) //$NON-NLS-1$
                setStyleIndex(SPEC_SPHERE);
            else if (str.equals("barrel")) //$NON-NLS-1$
                setStyleIndex(SPEC_BARREL);
        }

        if ((keys.containsKey("seamark:type") && keys.get("seamark:type").equals( //$NON-NLS-1$ //$NON-NLS-2$
                "beacon_special_purpose")) //$NON-NLS-1$
                || keys.containsKey("seamark:beacon_special_purpose:colour") //$NON-NLS-1$
                || keys.containsKey("seamark:beacon_special_purpose:shape")) { //$NON-NLS-1$
            if (keys.containsKey("seamark:beacon_special_purpose:shape") //$NON-NLS-1$
                    && keys.get("seamark:beacon_special_purpose:shape").equals("tower")) //$NON-NLS-1$ //$NON-NLS-2$
                setStyleIndex(SPEC_TOWER);
            else
                setStyleIndex(SPEC_BEACON);
        } else if (keys.containsKey("seamark:light_float:colour") //$NON-NLS-1$
                && keys.get("seamark:light_float:colour").equals("yellow")) //$NON-NLS-1$ //$NON-NLS-2$
            setStyleIndex(SPEC_FLOAT);

        keys = node.getKeys();
        if (keys.containsKey("seamark:topmark:shape")) { //$NON-NLS-1$
            str = keys.get("seamark:topmark:shape"); //$NON-NLS-1$

            if (str.equals("x-shape")) { //$NON-NLS-1$
                setTopMark(true);
            }
        }

        if (keys.containsKey("seamark:light:colour")) { //$NON-NLS-1$
            str = keys.get("seamark:light:colour"); //$NON-NLS-1$

            if (keys.containsKey("seamark:light:character")) { //$NON-NLS-1$
                setLightGroup(keys);

                String c = keys.get("seamark:light:character"); //$NON-NLS-1$
                if (getLightGroup() != "") //$NON-NLS-1$
                    c = c + "(" + getLightGroup() + ")"; //$NON-NLS-1$ //$NON-NLS-2$

                setLightChar(c);
                setLightPeriod(keys);
            }

            if (str.equals("white")) { //$NON-NLS-1$
                setFired(true);
                setLightColour("W"); //$NON-NLS-1$
            }
        }
    }

    public void setStyleIndex(int styleIndex) {
        super.setStyleIndex(styleIndex);
        if (styleIndex == SPEC_BARREL) {
            dlg.cM01Fired.setSelected(false);
            dlg.cM01Fired.setEnabled(false);
            dlg.cM01TopMark.setEnabled(true);
        } else {
            dlg.cM01Fired.setEnabled(true);
            dlg.cM01TopMark.setEnabled(true);
        }
    }

    public boolean isValid() {
        return (getBuoyIndex() > 0) && (getStyleIndex() > 0);
    }

    public void paintSign() {
        if (dlg.paintlock)
            return;
        super.paintSign();

        dlg.sM01StatusBar.setText(getErrMsg());

        if ((getBuoyIndex() > 0) && (getStyleIndex() > 0)) {
            dlg.tfM01Name.setEnabled(true);
            dlg.tfM01Name.setText(getName());
            dlg.cM01Radar.setEnabled(true);
            dlg.cM01Radar.setVisible(true);
            dlg.cM01Racon.setEnabled(true);
            dlg.cM01Racon.setVisible(true);

            dlg.cM01TopMark.setEnabled(true);
            dlg.cM01TopMark.setVisible(true);
            if (hasTopMark()) {
                dlg.cbM01TopMark.setEnabled(true);
                dlg.cbM01TopMark.setVisible(true);
            } else {
                dlg.cbM01TopMark.setVisible(false);
            }

            dlg.cM01Fog.setEnabled(true);
            dlg.cM01Fog.setVisible(true);

            dlg.cM01Fired.setVisible(true);
            dlg.cM01Fired.setEnabled(true);

            String image = "/images/Special_Purpose"; //$NON-NLS-1$

            switch (getStyleIndex()) {
            case SPEC_PILLAR:
                image += "_Pillar"; //$NON-NLS-1$
                break;
            case SPEC_SPAR:
                image += "_Spar"; //$NON-NLS-1$
                break;
            case SPEC_SPHERE:
                image += "_Sphere"; //$NON-NLS-1$
                break;
            case SPEC_BARREL:
                image += "_Barrel"; //$NON-NLS-1$
                break;
            case SPEC_FLOAT:
                image += "_Float"; //$NON-NLS-1$
                break;
            case SPEC_BEACON:
                image += "_Beacon"; //$NON-NLS-1$
                break;
            case SPEC_TOWER:
                image += "_Tower"; //$NON-NLS-1$
                break;
            default:
            }

            if (!image.equals("/images/Special_Purpose")) { //$NON-NLS-1$
                if (hasTopMark())
                    image += "_CrossY"; //$NON-NLS-1$
                image += ".png"; //$NON-NLS-1$
                dlg.lM01Icon.setIcon(new ImageIcon(getClass().getResource(image)));
            } else
                dlg.lM01Icon.setIcon(null);
        }
    }

    public void saveSign() {
        Node node = getNode();

        if (node == null) {
            return;
        }

        switch (getStyleIndex()) {
        case SPEC_PILLAR:
            super.saveSign("buoy_special_purpose"); //$NON-NLS-1$
            Main.main.undoRedo.add(new ChangePropertyCommand(node,
                    "seamark:buoy_special_purpose:shape", "pillar")); //$NON-NLS-1$ //$NON-NLS-2$
            Main.main.undoRedo.add(new ChangePropertyCommand(node,
                    "seamark:buoy_special_purpose:colour", "yellow")); //$NON-NLS-1$ //$NON-NLS-2$
            break;
        case SPEC_SPAR:
            super.saveSign("buoy_special_purpose"); //$NON-NLS-1$
            Main.main.undoRedo.add(new ChangePropertyCommand(node,
                    "seamark:buoy_special_purpose:shape", "spar")); //$NON-NLS-1$ //$NON-NLS-2$
            Main.main.undoRedo.add(new ChangePropertyCommand(node,
                    "seamark:buoy_special_purpose:colour", "yellow")); //$NON-NLS-1$ //$NON-NLS-2$
            break;
        case SPEC_SPHERE:
            super.saveSign("buoy_special_purpose"); //$NON-NLS-1$
            Main.main.undoRedo.add(new ChangePropertyCommand(node,
                    "seamark:buoy_special_purpose:shape", "sphere")); //$NON-NLS-1$ //$NON-NLS-2$
            Main.main.undoRedo.add(new ChangePropertyCommand(node,
                    "seamark:buoy_special_purpose:colour", "yellow")); //$NON-NLS-1$ //$NON-NLS-2$
            break;
        case SPEC_BARREL:
            super.saveSign("buoy_special_purpose"); //$NON-NLS-1$
            Main.main.undoRedo.add(new ChangePropertyCommand(node,
                    "seamark:buoy_special_purpose:shape", "barrel")); //$NON-NLS-1$ //$NON-NLS-2$
            Main.main.undoRedo.add(new ChangePropertyCommand(node,
                    "seamark:buoy_special_purpose:colour", "yellow")); //$NON-NLS-1$ //$NON-NLS-2$
            break;
        case SPEC_FLOAT:
            super.saveSign("light_float"); //$NON-NLS-1$
            Main.main.undoRedo.add(new ChangePropertyCommand(node,
                    "seamark:light_float:colour", "yellow")); //$NON-NLS-1$ //$NON-NLS-2$
            break;
        case SPEC_BEACON:
            super.saveSign("beacon_special_purpose"); //$NON-NLS-1$
            Main.main.undoRedo.add(new ChangePropertyCommand(node,
                    "seamark:beacon_special_purpose:colour", "yellow")); //$NON-NLS-1$ //$NON-NLS-2$
            break;
        case SPEC_TOWER:
            super.saveSign("beacon_special_purpose"); //$NON-NLS-1$
            Main.main.undoRedo.add(new ChangePropertyCommand(node,
                    "seamark:beacon_special_purpose:shape", "tower")); //$NON-NLS-1$ //$NON-NLS-2$
            Main.main.undoRedo.add(new ChangePropertyCommand(node,
                    "seamark:beacon_special_purpose:colour", "yellow")); //$NON-NLS-1$ //$NON-NLS-2$
            break;
        default:
        }
        saveTopMarkData("x-shape", "yellow"); //$NON-NLS-1$ //$NON-NLS-2$
        saveLightData("white"); //$NON-NLS-1$
        saveRadarFogData();
    }

    public void setLightColour() {
        super.setLightColour("W"); //$NON-NLS-1$
    }

}

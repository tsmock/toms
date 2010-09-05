// License: GPL. For details, see LICENSE file.
// Copyright (c) 2009 / 2010 by Werner Koenig

package toms;

import static org.openstreetmap.josm.gui.help.HelpUtil.ht;
import static org.openstreetmap.josm.tools.I18n.marktr;

import java.awt.event.KeyEvent;
import java.util.Properties;
import java.util.Set;

import javax.swing.JMenuItem;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.gui.MainMenu;
import org.openstreetmap.josm.gui.MapView.LayerChangeListener;
import org.openstreetmap.josm.gui.layer.Layer;
import org.openstreetmap.josm.plugins.Plugin;
import org.openstreetmap.josm.plugins.PluginInformation;
import org.openstreetmap.josm.gui.MapFrame;

import toms.dialogs.SmpDialogAction;
import toms.seamarks.SeaMark;

// Kommentar zum Ausprobieren von svn
// und nochmal
// und nochmal, diesmal mit kwallet als Passwortmanager

public class Toms extends Plugin {

    private JMenuItem Smp;
    private SmpDialogAction SmpDialog;

    public Toms(PluginInformation info) {
        super(info);

        String os = ""; //$NON-NLS-1$
        String userHome = ""; //$NON-NLS-1$

        SmpDialog = new SmpDialogAction();
        Smp = Main.main.menu.toolsMenu.add(SmpDialog);
        // Smp = MainMenu.add(Main.main.menu.toolsMenu, SmpDialog);

        SmpDialog.setSmpItem(Smp);
        SmpDialog.setOs(os);
        SmpDialog.setUserHome(userHome);
        Smp.setEnabled(false);

        System.out.println(Messages.getString("Toms.2")); //$NON-NLS-1$
    }

    @Override
    public void mapFrameInitialized(MapFrame oldFrame, MapFrame newFrame) {
        if (oldFrame == null && newFrame != null) {
            Smp.setEnabled(true);
        } else {
            Smp.setEnabled(false);
            SmpDialog.CloseDialog();
        }
    }

}

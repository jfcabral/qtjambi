/****************************************************************************
**
** Copyright (C) 2012 Darryl L. Miles.  All rights reserved.
** Copyright (C) 2012 D L Miles Consulting Ltd.  All rights reserved.
**
** This file is part of Qt Jambi.
**
**
** $BEGIN_LICENSE$
** GNU Lesser General Public License Usage
** This file may be used under the terms of the GNU Lesser
** General Public License version 2.1 as published by the Free Software
** Foundation and appearing in the file LICENSE.LGPL included in the
** packaging of this file.  Please review the following information to
** ensure the GNU Lesser General Public License version 2.1 requirements
** will be met: http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
** 
** In addition, as a special exception, the copyright holders grant you
** certain additional rights. These rights are described in the Nokia Qt
** LGPL Exception version 1.0, included in the file LGPL_EXCEPTION.txt in
** this package.
** 
** GNU General Public License Usage
** Alternatively, this file may be used under the terms of the GNU
** General Public License version 2.0 as published by the Free Software
** Foundation and appearing in the file LICENSE.GPL2 included in the
** packaging of this file.  Please review the following information to
** ensure the GNU General Public License version 2.0 requirements will be
** met: http://www.gnu.org/licenses/gpl-2.0.html
**
** GNU General Public License Usage
** Alternatively, this file may be used under the terms of the GNU
** General Public License version 3.0 as published by the Free Software
** Foundation and appearing in the file LICENSE.GPL3 included in the
** packaging of this file.  Please review the following information to
** ensure the GNU General Public License version 3.0 requirements will be
** met: http://www.gnu.org/copyleft/gpl.html
** $END_LICENSE$
**
** This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
** WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
**
****************************************************************************/

package com.trolltech.autotests;

import static org.junit.Assert.assertTrue;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import java.util.Date;

import org.junit.Test;

import com.trolltech.qt.QThread;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.QEvent;
import com.trolltech.qt.core.QEventLoop;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QKeyEvent;
import com.trolltech.qt.gui.QClipboard;
import com.trolltech.qt.gui.QMainWindow;

public class TestClipboard /*extends QApplicationTest*/ {
    public static class Foo implements ClipboardOwner {
    private void grabClipboard() throws Exception {
        System.out.println("Clipboard updated");
        try {
            Thread.sleep(100);
        } catch(InterruptedException eat) {
        }

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        Transferable t = clipboard.getContents(null);

        if(t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            System.out.println("\t text: " + t.getTransferData(DataFlavor.stringFlavor).toString());
        }

        if(t.isDataFlavorSupported(DataFlavor.imageFlavor)) {
            System.out.println("\t image");
        }

        if(t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            System.out.println("\t file");
        }
    }

    private void updateClipboard(String s) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        clipboard.setContents(new StringSelection(s), this);
    }

    /**
     *  ClipboardOwner interface method
     */
    public void lostOwnership(Clipboard aClipboard, Transferable aContents) {
        // noop
    }

    // Sarogate heavy weight thing to cause memory re-use
    private void runQThread() {
        TestRunnable testRunnable = new TestRunnable();

        QThread qThread = new QThread(testRunnable);

        qThread.start();
    }

    private class TestRunnable implements Runnable {
        public TestRunnable() {
        }

        public void run() {
            System.out.println("TestRunnable run() start");

            System.out.println("TestRunnable run() finished");
        }
    }

//    public volatile QClipboard clipboard;
    private void connectGrabClipboard() {
        // Accessing QApplication.clipboard() here should be enough to cause crash on exit
        QClipboard clipboard = QApplication.clipboard();
        clipboard.dataChanged.connect(this, "grabClipboard()");
    }

    private void disconnectGrabClipboard() {
        QClipboard clipboard = QApplication.clipboard();
        if(clipboard != null) {
        clipboard.dataChanged.disconnect(this, "grabClipboard()");
        clipboard.dataChanged.disconnect(this);
        clipboard.dataChanged.disconnect();
        clipboard = null;
        }
    }

    //@Test
    public void testClipboardCrash() {
        QApplication.initialize(new String[0]);

        connectGrabClipboard();

        QMainWindow mainWindow = new QMainWindow() {
            @Override
            protected void keyPressEvent(final QKeyEvent event) {
                int key = event.key();
                System.out.println("keyPressEvent() event.key()="+key);
                if(key == Qt.Key.Key_G.value()) {
                    System.out.println("keyPressEvent(): GC");
                    System.gc();
                }
                if(key == Qt.Key.Key_T.value()) {
                    System.out.println("keyPressEvent(): THREAD");
                    runQThread();
                }
                if(key == Qt.Key.Key_U.value()) {
                    System.out.println("keyPressEvent(): updateClipboard();");
                    updateClipboard("QtJambi clipboard test at " + new Date().toString());
                }
                if(key == Qt.Key.Key_Q.value()) {
                    System.out.println("keyPressEvent(): QUIT");
                    QApplication.quit();
                }
            }
        };
        mainWindow.show();

        QApplication.processEvents();
        QApplication.processEvents(QEventLoop.ProcessEventsFlag.DeferredDeletion);

        QApplication.postEvent(mainWindow, new QKeyEvent(QEvent.Type.KeyPress, Qt.Key.Key_G.value(), Qt.KeyboardModifier.createQFlags(Qt.KeyboardModifier.NoModifier)));
        QApplication.postEvent(mainWindow, new QKeyEvent(QEvent.Type.KeyPress, Qt.Key.Key_T.value(), Qt.KeyboardModifier.createQFlags(Qt.KeyboardModifier.NoModifier)));
        QApplication.postEvent(mainWindow, new QKeyEvent(QEvent.Type.KeyPress, Qt.Key.Key_U.value(), Qt.KeyboardModifier.createQFlags(Qt.KeyboardModifier.NoModifier)));
        QApplication.postEvent(mainWindow, new QKeyEvent(QEvent.Type.KeyPress, Qt.Key.Key_G.value(), Qt.KeyboardModifier.createQFlags(Qt.KeyboardModifier.NoModifier)));
        QApplication.postEvent(mainWindow, new QKeyEvent(QEvent.Type.KeyPress, Qt.Key.Key_Q.value(), Qt.KeyboardModifier.createQFlags(Qt.KeyboardModifier.NoModifier)));
        QApplication.postEvent(mainWindow, new QKeyEvent(QEvent.Type.KeyPress, Qt.Key.Key_G.value(), Qt.KeyboardModifier.createQFlags(Qt.KeyboardModifier.NoModifier)));

        QApplication.execStatic();

        disconnectGrabClipboard();

        try {
            Thread.sleep(100);  // wait for runQThread() to die
        } catch(InterruptedException eat) {
        }

        mainWindow.close();
        mainWindow = null;

        QApplication.shutdown();
    }
    }

    @Test
    public void testClipboardCrash() {
        new Foo().testClipboardCrash();
    }

    public static void main(String args[]) {
        org.junit.runner.JUnitCore.main(TestClipboard.class.getName());
    }
}
/*   Ported from: doc.src.i18n.qdoc
<snip>
//! [0]
        LoginWidget::LoginWidget()
        {
            QLabel *label = new QLabel(tr("Password:"));
            ...
        }
//! [0]


//! [1]
        void some_global_function(LoginWidget *logwid)
        {
            QLabel *label = new QLabel(
                        LoginWidget::tr("Password:"), logwid);
        }

        void same_global_function(LoginWidget *logwid)
        {
            QLabel *label = new QLabel(
                        qApp->translate("LoginWidget", "Password:"), logwid);
        }
//! [1]


//! [2]
        QString FriendlyConversation::greeting(int type)
        {
            static const char *greeting_strings[] = {
                QT_TR_NOOP("Hello"),
                QT_TR_NOOP("Goodbye")
            };
            return tr(greeting_strings[type]);
        }
//! [2]


//! [3]
        static const char *greeting_strings[] = {
            QT_TRANSLATE_NOOP("FriendlyConversation", "Hello"),
            QT_TRANSLATE_NOOP("FriendlyConversation", "Goodbye")
        };

        QString FriendlyConversation::greeting(int type)
        {
            return tr(greeting_strings[type]);
        }

        QString global_greeting(int type)
        {
            return qApp->translate("FriendlyConversation",
                                   greeting_strings[type]);
        }
//! [3]


//! [4]
        void FileCopier::showProgress(int done, int total,
                                      const QString &currentFile)
        {
            label.setText(tr("%1 of %2 files copied.\nCopying: %3")
                          .arg(done)
                          .arg(total)
                          .arg(currentFile));
        }
//! [4]


//! [5]
        QString s1 = "%1 of %2 files copied. Copying: %3";
        QString s2 = "Kopierer nu %3. Av totalt %2 filer er %1 kopiert.";

        qDebug() << s1.arg(5).arg(10).arg("somefile.txt");
        qDebug() << s2.arg(5).arg(10).arg("somefile.txt");
//! [5]


//! [6]
    5 of 10 files copied. Copying: somefile.txt
    Kopierer nu somefile.txt. Av totalt 10 filer er 5 kopiert.
//! [6]


//! [7]
        HEADERS         = funnydialog.h \
                          wackywidget.h
        SOURCES         = funnydialog.cpp \
                          main.cpp \
                          wackywidget.cpp
        FORMS           = fancybox.ui
        TRANSLATIONS    = superapp_dk.ts \
                          superapp_fi.ts \
                          superapp_no.ts \
                          superapp_se.ts
//! [7]


//! [8]
        int main(int argc, char *argv[])
        {
            QApplication app(argc, argv);

            QTranslator qtTranslator;
            qtTranslator.load("qt_" + QLocale::system().name());
            app.installTranslator(&qtTranslator);

            QTranslator myappTranslator;
            myappTranslator.load("myapp_" + QLocale::system().name());
            app.installTranslator(&myappTranslator);

            ...
            return app.exec();
        }
//! [8]


//! [9]
        QString string = ...; // some Unicode text

        QTextCodec *codec = QTextCodec::codecForName("ISO 8859-5");
        QByteArray encodedString = codec->fromUnicode(string);
//! [9]


//! [10]
        QByteArray encodedString = ...; // some ISO 8859-5 encoded text

        QTextCodec *codec = QTextCodec::codecForName("ISO 8859-5");
        QString string = codec->toUnicode(encodedString);
//! [10]


//! [11]
        void Clock::setTime(const QTime &time)
        {
            if (tr("AMPM") == "AMPM") {
                // 12-hour clock
            } else {
                // 24-hour clock
            }
        }
//! [11]


//! [12]
    void QWidget::changeEvent(QEvent *event)
    {
        if (e->type() == QEvent::LanguageChange) {
            titleLabel->setText(tr("Document Title"));
            ...
            okPushButton->setText(tr("&OK"));
        } else
            QWidget::changeEvent(event);
    }
//! [12]


</snip>
*/
import com.trolltech.qt.*;
import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.xml.*;
import com.trolltech.qt.network.*;
import com.trolltech.qt.sql.*;
import com.trolltech.qt.svg.*;


public class doc_src_i18n {
    public static void main(String args[]) {
        QApplication.initialize(args);
//! [0]
        LoginWidget.LoginWidget()
        {
            QLabel abel = new QLabel(tr("Password:"));
            ...
        }
//! [0]


//! [1]
        void some_global_function(LoginWidget ogwid)
        {
            QLabel abel = new QLabel(
                        LoginWidget.tr("Password:"), logwid);
        }

        void same_global_function(LoginWidget ogwid)
        {
            QLabel abel = new QLabel(
                        qApp.translate("LoginWidget", "Password:"), logwid);
        }
//! [1]


//! [2]
        StringsFriendlyConversation.greeting(int type)
        {
            static char reeting_strings[] = {
                QT_TR_NOOP("Hello"),
                QT_TR_NOOP("Goodbye")
            };
            return tr(greeting_strings[type]);
        }
//! [2]


//! [3]
        static char reeting_strings[] = {
            QT_TRANSLATE_NOOP("FriendlyConversation", "Hello"),
            QT_TRANSLATE_NOOP("FriendlyConversation", "Goodbye")
        };

        StringsFriendlyConversation.greeting(int type)
        {
            return tr(greeting_strings[type]);
        }

        Stringsglobal_greeting(int type)
        {
            return qApp.translate("FriendlyConversation",
                                   greeting_strings[type]);
        }
//! [3]


//! [4]
        void FileCopier.showProgress(int done, int total,
                                      StringsurrentFile)
        {
            label.setText(tr("%1 of %2 files copied.\nCopying: %3")
                          .arg(done)
                          .arg(total)
                          .arg(currentFile));
        }
//! [4]


//! [5]
        Stringss1 = "%1 of %2 files copied. Copying: %3";
        Stringss2 = "Kopierer nu %3. Av totalt %2 filer er %1 kopiert.";

        qDebug() << s1.arg(5).arg(10).arg("somefile.txt");
        qDebug() << s2.arg(5).arg(10).arg("somefile.txt");
//! [5]


//! [6]
    5 of 10 files copied. Copying: somefile.txt
    Kopierer nu somefile.txt. Av totalt 10 filer er 5 kopiert.
//! [6]


//! [7]
        HEADERS         = funnydialog.h \
                          wackywidget.h
        SOURCES         = funnydialog.cpp \
                          main.cpp \
                          wackywidget.cpp
        FORMS           = fancybox.ui
        TRANSLATIONS    = superapp_dk.ts \
                          superapp_fi.ts \
                          superapp_no.ts \
                          superapp_se.ts
//! [7]


//! [8]
        int main(int argc, char rgv[])
        {
            QApplication app(argc, argv);

            QTranslator qtTranslator;
            qtTranslator.load("qt_" + QLocale.system().name());
            app.installTranslator(tTranslator);

            QTranslator myappTranslator;
            myappTranslator.load("myapp_" + QLocale.system().name());
            app.installTranslator(yappTranslator);

            ...
            return app.exec();
        }
//! [8]


//! [9]
        Stringsstring = ...; // some Unicode text

        QTextCodec odec = QTextCodec.codecForName("ISO 8859-5");
        QByteArray encodedString = codec.fromUnicode(string);
//! [9]


//! [10]
        QByteArray encodedString = ...; // some ISO 8859-5 encoded text

        QTextCodec odec = QTextCodec.codecForName("ISO 8859-5");
        Stringsstring = codec.toUnicode(encodedString);
//! [10]


//! [11]
        void Clock.setTime(QTime ime)
        {
            if (tr("AMPM") == "AMPM") {
                // 12-hour clock
            } else {
                // 24-hour clock
            }
        }
//! [11]


//! [12]
    void QWidget.changeEvent(QEvent vent)
    {
        if (e.type() == QEvent.LanguageChange) {
            titleLabel.setText(tr("Document Title"));
            ...
            okPushButton.setText(tr("K"));
        } else
            QWidget.changeEvent(event);
    }
//! [12]


    }
}
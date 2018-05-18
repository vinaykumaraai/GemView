SET APPDIR=%~dp0
java -cp "$APPDIR/src:$APPDIR/bin:D:/Clases/Cursos_EjemploProgramas/2-Otros/4-FrameWorks Interfaz de Usuario/GWT/GWT/libs/gwt-2.5.1/gwt-user.jar:D:/Clases/Cursos_EjemploProgramas/2-Otros/4-FrameWorks Interfaz de Usuario/GWT/GWT/libs/gwt-2.5.1/gwt-dev.jar" com.google.gwt.i18n.tools.I18NSync -out $APPDIR/src main.java.com.luretechnologies.vtsuite.vtams.client.classes.i18n.Messages -createMessages ;


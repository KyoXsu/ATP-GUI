# ATP-GUI

## Description
ATP est un logiciel de configuration automatique de téléphones portables.
(A suivre).

## Compiler le projet pour MacOs

D'abord je dois compiler mes classes, je peux le faire avec intellij idea ou à l'aide de la commande suivante : (à revoir)
```
jar cfm apt.jar target/classes/META-INF/MANIFEST.MF -C target/classes/org/kyoxsu/core/atp/ .
```

Attention, lorsque je compile avec intellij idea, il faut faire :
project structure > artifacts
On crée un nouveau module with dependencies
Et il suffit de build le jar.

Ensuite je crée le jre portable, qui sera inclu dans l'application :
```
jlink \
  --add-modules java.base,java.desktop,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.web \
  --module-path /Users/kyoxsu/Library/Java/JavaVirtualMachines/openjdk-22.0.1/Contents/Home/jmods:/Users/kyoxsu/Library/Java/JavaVirtualMachines/javafx-sdk-22.0.1/lib \
  --output custom-jre
```

Les fichiers natifs JavaFX (comme les .dylib pour macOS) doivent être copiés dans le JRE généré. jlink ne les inclut pas automatiquement, donc il faut les ajouter manuellement.
```
cp * /Users/kyoxsu/Desktop/programmation/atp/atp/jarBuilder/custom-jre/lib/
```

Enfin j'empaquête le tout
```
jpackage \
  --input ./jarBuilder \
  --name atp \
  --main-jar ./atp.jar \
  --main-class org.kyoxsu.core.atp.Main \
  --type dmg \
  --runtime-image ./jarBuilder/custom-jre \
  --java-options "-Dprism.forceGPU=true" \
  --icon ./src/main/resources/icons/iconApp.icns \
  --app-version 1.0.0 \
  --description "Une application Java standalone permettant de configurer des téléphones automatiquement" \
  --vendor ""
```

##  Compiler le projet pour Windows

Pour compiler l'installeur msi, il faut télécharger les [outils wix](https://github.com/wixtoolset/wix3/releases/tag/wix3141rtm).


La commande pour créer le jre nécéssaire :
```
& 'C:\Program Files\Java\jdk-22\bin\jlink' `
--module-path `
  "C:\Program Files\Java\jdk-22\jmods;C:\Users\esteb\.m2\repository\org\openjfx\javafx-controls\22.0.1;C:\Users\esteb\.m2\repository\org\openjfx\javafx-fxml\22.0.1;C:\Users\esteb\.m2\repository\org\openjfx\javafx-graphics\22.0.1;C:\Users\esteb\.m2\repository\org\openjfx\javafx-base\22.0.1" `
--add-modules `
  java.base,java.desktop,javafx.base,javafx.controls,javafx.fxml,javafx.graphics `
--output `
  "C:\Users\esteb\Desktop\projets\atp\custom-jre"
```

Pour faire l'installateur :
```
& 'C:\Program Files\Java\jdk-22\bin\jpackage.exe' `
--input ".\jarBuilder" `
--name atp `
--main-jar atp.jar `
--main-class com.example.atp.Launcher `
--type msi `
--runtime-image "C:\Users\esteb\Desktop\projets\atp\jarBuilder\custom-jre" `
--app-version 1.0.0 `
--description "Une application Java standalone permettant de configurer des téléphones automatiquement" `
--vendor "Esteban Gonzalez--Tessier" `
--win-shortcut `
--verbose
```

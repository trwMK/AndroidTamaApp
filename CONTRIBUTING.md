# Contribute to Tamagotchi-Projekt  
In dieser Datei sind alle Regeln/Hilfen zur ordnungsgemäßen Arbeit an diesem Projekt.

CONTRIBUTING
======
### Merge Request

>Ein Merge Request ist eine Anfrage zur zusammenschließung zweier Branches.


##### 1. Squash Commit

Wenn ein Merge Request erstellt wird, muss darauf geachtet werden, welcher Branch gemerged wird.  
Bei kleineren Änderungen/Commits [max 5 Commits] dürfen die Commits auf einen zusammengedrückt werden:
* [x]  `Squash commits when merge request is accepted.`  

Bei größeren und vielen Commits sollten diese nicht zusammengedrückt werden, damit man die History noch nachvollziehen kann im Falle eines Fehlers.  
* [ ]  `Squash commits when merge request is accepted.`

##### 2. Merge Request Template
Bitte nutzt das Basis Template, wenn Ihr einen Merge Request erstellt. Dann haben alle eine gute Übersicht, was dieser Merge Request alles beinhaltet.


Issues
======
### Issue Weight

>Beim Issue Weight setzt man den Arbeitsaufwand nach der Gewichtung der Arbeit.  
Dies bedeutet, dass ein Issue mit der höchsten Zahl 9 am aufwändigsten ist.  
Diese Issues werden dann nochmal unter/gelinkte/relationen Issues besitzen, welche dann möglicherweise auch eine Gewichtung haben, welche dann niedriger ausfallen.   

9 - Epic Feature (wird eigentlich hier nicht programmiert)  
8 - Feature (Grafische&Technische Implementierung)  
7 - Minor-Feature (Technische Implementierung(Nur Code[Klassen,Methoden,Variablen,...]))  
6 - Bug Fixing (Features,Minor-Features)   
5 - Dokumentation (Wiki, Issues Docs)  
4 - Sprites Erstellung/Updates/Behebung 2 (EvoStufen,Animationen)  
3 - Sprites Erstellung/Updates/Behebung 1 (Möbel, Items, Objekte[Sonne,Mond], Essen...)  
2 - Bilder Erstellung/Updates/Behebung (Hintergründe)  
1 - Text Erstellung/Updates/Behebung (Kleinere Text Updates)  

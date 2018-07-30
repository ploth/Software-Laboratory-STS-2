# digit-recognition

## Info

Dieses Projekt kann in eclipse importiert werden.

Dieses Programm bietet zurzeit zwei Möglichkeiten Ziffern zwischen 0-9 automatisch zu erkennen. 
Die Möglichkeiten unterscheiden sich in den verwendeten Algorithmen. Zur Auswahl steht der KNN (k-nearest-neighbor) und der KMean (k-means clustering) Algorithmus.

## Daten importieren

Zur Klassifikation von Ziffern mit dem KNN Algorithmus sind Trainingsdaten notwenig, die nach dem Start des Programmes im Tab "Input/Output Data" im Abschnitt "Training data" geladen werden können.
Im Abschnitt "Classify data" sind die Daten zu importieren, die man klassifizieren möchte.
Jeweils ist es möglich nicht den kompletten Inhalt zu laden, sondern nur Bereiche.

Zur Klassifikaton von Ziffern mit dem KMean Algorithmus sind keine Trainingsdaten notwenig, da ein Clustering mit allen in der Datenbank befindlichen Einträgen stattfindet. Es ist also irrelevant wieviele Trainingsdaten oder zu klassifizierende Daten sich in der Datenbank befinden.

Zudem besteht immer die Möglichkeit die komplette Datenbank aller korrekt Klassifizierten - sprich Trainingsdaten zu exportieren.

## Datenbankverwaltung

Ansehen lässt sich der komplette Inhalt der Datenbank im Tab "Database". Zwei Pfeile oder das Eintragen einer Indexnummer zeigen das gewünschte Element.

## Algorithmen

Die Algorithmen lassen sich im Tab "Algorithms" starten.

Mit dem KNN Algorithmus kann sofort losgelegt werden, sofern Trainingsdaten und zu klassifizierende Daten vorhanden sind.
Mit einem Klick auf "Classify data", sind ein k und eine Abstandsfunktion zu wählen. 
Dann erscheint ein Fenster, auf dem das zu klassifizierende Objekt, die vom Algorithmus erkannte Klassifikation und ggf. die korrekte Klassifikation angezeigt wird (MNIST oder csv Daten müssen dafür im Tab "Input/Output data" im Abschnitt "Classify data" importiert worden sein).

Um den KMean Algorithmus zu verwenden ist das Clustering erstmals durchzuführen. Nach dem Klick auf "Perform k-Means clustering", ist ein k, eine Abstandsfunktion, maximale Anzahl an Iterationen und ein Schwellenwert zu wählen. 
Dann erscheint ein Fenster welches den Durchschnitt aller im jeweiligen Cluster befindlichen Objekte anzeigt. Die Cluster ist nun per Hand zu klassifzieren (maximal k Cluster).
Nun ist es möglich schnell neue unbekannte Elemente zu erkennen, nachdem das Clustering ausgeführt wurde. Dafür sind natürlich neue zu klassifizierende Objekte notwendig, die über den Tab "Input/Output data" importiert werden müssen.

Beide Algorithmen bieten die Möglichkeit Testläufe durchzuführen, welche im Nachhinein eine Statistik anzeigt. Dafür sind Trainingsdaten und zu klassifizierende Daten mit korrekter Klassifikation notwenig, um selbstständig zu entscheiden, ob die vom Algorithmus gewähle Klassifikation richtig oder falsch ist.
Als Trainingsdaten können bereits vorhandene aus der Datenbank verwendet werden oder es sind welche aus dem MNIST Datensatz oder aus csv Dateien zu importieren.
Zu klassifzierende Objekte müssen von dem MNIST Datensatz oder aus csv Dateien kommen, da zwingend die korrekte Klassifizierung notwendig ist.

## Datenstruktur

Intern werden in der Datenbank nur Objekte vom Typ DatabaseElement gespeichert, welche alle nötigen Information für die Algorithmen enthalten und speichern.

## Kontakt

[tschattschneider](https://github.com/tschattschneider), [ploth](https://github.com/ploth)

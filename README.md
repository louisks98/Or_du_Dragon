L'Or du Dragon
Cette année je vais découper le projet en deux livrables. Même si je n'ai pas encore rédigé l'énoncé officiel, je peux quand même vous dire tout de suite de quoi il s'agit :

il s'agit d'une application cliente en mode graphique pour le jeu l'Or du Dragon
elle sera réalisée à l'aide de la bibliothèque JavaFX
au lancement de l'application, celle-ci se connecte au serveur de carte (149.56.47.97:51005) pour obtenir la liste des noeuds (villages) et des arcs (chemins)
l'application dessine alors la carte en prenant soin de conserver dans une structure de données (probablement un ArrayList) la liste des noeuds
ensuite, l'application se connecte au serveur de position (149.56.47.97:51006) pour être renseigné sur les entités présentes sur les noeuds et mettre continuellement à jour la carte
dernière chose demandée, en cliquant sur un noeud, les coordonnées de celui-ci doivent s'afficher dans le coin inférieur droit de la carte.
Voilà, c'est tout ce qui est demandé pour le premier livrable. Un énoncé plus complet sera disponible lundi. Je vous rappelle que les deux premiers serveurs (serveur de carte et serveur de position) sont déjà fonctionnels, même si le deuxième envoie pour le moment des données aléatoires.

Même s'il y aura beaucoup communication dans tout ça et que le collège est plutôt restrictif au niveau des accès réseau, je ferai tout en mon possible pour que vous puissiez développer et tester (jouer) de l'intérieur ou de l'extérieur du collège.


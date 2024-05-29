import { Utilisateur } from './utilisateur.model';
import { File } from './file.model';

export class Group{
    constructor(
        public id: number,
        public titre: string,
        public description: string,
        public projetPhoto: string,
        public publique: boolean,
        public dateCreation: Date,
        public dateModification: Date,
        public dateSuppression: Date,
        public admin : Utilisateur,
        public utilisateurs: Utilisateur[],
        public fichiers: File[],
    ) {}
}
import { Utilisateur } from './utilisateur.model';

export class Group{
    constructor(
        public id: number,
        public titre: string,
        public description: string,
        public projectPhoto: string,
        public publique: boolean,
        public dateCreation: string,
        public dateModification: string,
        public dateSuppression: string,
        public userAdmin : Utilisateur,
    ) {}
}
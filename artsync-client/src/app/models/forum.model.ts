import { Utilisateur } from './utilisateur.model';

export class Forum{
    constructor(
        public id: number,
        public titre: string,
        public contenu: string,
        public date: string,
        public publique: boolean,
        public utilisateur : Utilisateur,
    ) {}
}
import { Utilisateur } from './utilisateur.model';

export class File{
    constructor(
        public id: number,
        public urlMedia: string,
        public utilisateur : Utilisateur,
        public type: string,
    ){}
}
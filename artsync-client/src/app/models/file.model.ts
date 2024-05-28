import { Utilisateur } from './utilisateur.model';

export class File{
    constructor(
        public id: number,
        public urlMedia: string,
        public groupId: number,
        public postId: number,
        public user : Utilisateur,
        public type: string,
    ){}
}
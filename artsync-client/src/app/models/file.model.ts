import { Utilisateur } from './utilisateur.model';

export class File{
    constructor(
        public id: number,
        public urlMedia: string,
        public groupId: number | null,
        public postId: number | null,
        public user : Utilisateur,
        public type: string,
    ){}
}
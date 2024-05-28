import { Utilisateur } from './utilisateur.model';

export class Comment{
    constructor(
        public id: number,
        public message: string,
        public forumId: number | null,
        public postId: number | null,
        public user : Utilisateur,
    ) {}
}
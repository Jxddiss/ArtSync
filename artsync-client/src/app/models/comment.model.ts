import { Utilisateur } from './utilisateur.model';

export class Comment{
    constructor(
        public id: number,
        public message: string,
        public utilisateur : Utilisateur,
    ) {}
}
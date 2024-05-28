import { Comment } from "./comment.model";
import { Utilisateur } from "./utilisateur.model";

const BASE_POST_PHOTO_PATH : string= "https://robohash.org/";

export class Post {
    constructor(
        public id:number,
        public date:Date,
        public titre:string,
        public texte:string,
        public listeFichiers:any[],
        public type:string,
        public utilisateur:Utilisateur,
        public listeCommentaires:Comment[]
    ){
        this.listeFichiers[0].urlMedia = BASE_POST_PHOTO_PATH + listeFichiers[0].urlMedia
    }
}

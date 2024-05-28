export class File{
    constructor(
        public id: number,
        public urlMedia: string,
        public groupId: number,
        public postId: number,
        public userId : number,
        public type: string,
    ){}
}
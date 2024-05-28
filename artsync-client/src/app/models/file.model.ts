export class File{
    constructor(
        private id: number,
        private urlMedia: string,
        private groupId: number,
        private postId: number,
        private userId : number,
        private type: string,
    ){}
}
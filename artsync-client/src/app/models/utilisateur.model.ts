export class Utilisateur {
    constructor(
        public id: number,
        public pseudo:string,
        public prenom:string,
        public nom:string,
        public email:string,
        public photoUrl:string,
        public specialisation:string,
        public roleName:string,
        public isActive:boolean
    ){
        this.photoUrl = `https://robohash.org/${photoUrl}`
    }
}

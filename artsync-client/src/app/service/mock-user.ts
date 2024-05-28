import { Utilisateur } from "../models/utilisateur.model";

export const USERS: Utilisateur[]=[]

for (let i = 1; i <= 10; i++) {
    const user = new Utilisateur(
        i,
        `pseudo${i}`,
        `prenom${i}`,
        `nom${i}`,
        `email${i}@example.com`,
        `${i}`,
        `specialisation${i}`,
        `roleName${i}`,
        i % 2 === 0 // alternate isActive between true and false
    );
    USERS.push(user);
}

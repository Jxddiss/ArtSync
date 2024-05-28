import { Post } from "../models/post.model";
import { Utilisateur } from "../models/utilisateur.model";

export const POSTS: Post[] = [];

for (let i = 1; i <= 10; i++) {
    const post = new Post(
        i,
        new Date(),
        `Post ${i}`,
        `Description ${i}`,
        [{ urlMedia: `image${i}.jpg` }],
        "Type",
        new Utilisateur(
            i,
            `pseudo${i}`,
            `prenom${i}`,
            `nom${i}`,
            `email${i}@example.com`,
            `${i}`,
            `specialisation${i}`,
            `roleName${i}`,
            i % 2 === 0 // alternate isActive between true and false
        ),
        [] 
    );
    POSTS.push(post);
}

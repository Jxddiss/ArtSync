import { Comment } from '../models/comment.model';
import { USERS } from './mock-user';

export const COMMENTS: Comment[] = [
    new Comment(
        1,
        'Superbe!',
        USERS[0]
    ),
    new Comment(
        2,
        'Magnifique!',
        USERS[1]
    ),
    new Comment(
        3,
        'Bravo!',
        USERS[2]
    ),
    new Comment(
        4,
        'Splendide!',
        USERS[3]
    ),
    new Comment(
        5,
        'Excellent!',
        USERS[4]
    )
]
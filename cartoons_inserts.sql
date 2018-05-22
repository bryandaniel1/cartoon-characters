INSERT INTO cartoon (title, description) VALUES ('Masters of the Universe', 
'This series follows the struggles between the forces of good and evil on the planet Eternia. The planet is ruled by King Randor and Queen Marlena. He-Man, the secret identity of their son, Prince Adam, is known only to the Sorceress of Castle Grayskull, Man-At-Arms, and Orko. He-Man and his allies must constantly battle with the evil warlord, Skeletor, and his henchmen to protect Eternia.');

INSERT INTO cartoon_location (location_name, description, cartoon_id) VALUES ('Planet Eternia', 
'This planet is the location for the cartoon series, Masters of the Universe.', 1), ('Castle Grayskull', 
'This fortress on planet Eternia is home to the Sorceress and is a frequent site of battle between the forces of good and evil.', 1), 
('Royal Palace', 
'This palace is home to the royal family and the government of Eternia. Prince Adam, King Randor, Queen Marlena, Man-At-Arms, Orko, Teela, and Cringer reside at the palace.', 1), 
('Snake Mountain', 
'The stronghold of the arch-villain, Skeletor, this mountain appears to be embraced by a giant snake.', 1);

INSERT INTO cartoon_character (character_name, description, character_home) VALUES ('He-Man', 
'He-Man is the main character of the Masters of the Universe series. His true identity, Prince Adam, is known only to the Sorceress, Mana-At-Arms, and Orko. Prince Adam transforms into He-Man by holding the Power Sword aloft and declaring, "By the power of Grayskull... I have the power!".', 1), 
('Skeletor', 
'Skeletor, the primary antagonist of the series, is a powerful sorcerer skilled in black magic. He resides at Snake Mountain, plotting to defeat He-Man and conquer Eternia. His origins are uncertain, known only to have come from another dimension.', 4), 
('Teela', 
'The biological daughter of the Sorceress and the adopted daughter of Man-At-Arms, Teela is the Captain of the Royal Guard and resposible for the protection and training of Prince Adam.', 3), 
('Man-At-Arms', 
'Man-At-Arms is the title given to Duncan, an ally of He-Man and the master of arms and combat instruction for the royal family of Eternia. Man-At-Arms also uses his engineering skills to devlop new weapons.', 3), 
('Sorceress of Castle Grayskull', 
'The Sorceress, guardian of the wisdom and secrets of Castle Grayskull, resides in the castle and is counselor to the heroes on Eternia. She gave Prince Adam the ability to become He-Man with the Sword of Power and also allowed Princess Adora to become She-Ra by proving her the Sword of Protection. She is able to communicate telepathically with allies and transform into the falcon, Zoar, to travel outside the castle.', 2), 
('Mer-Man', 
'Mer-Man is an ocean warlord with the ability to control aquatic life. He is one of Skeletor''s loyal henchmen, so is often employed to do battle against Eternia''s heroes.', 1), 
('Beast-Man', 
'Beast-Man is Skeletor''s most loyal henchman involved frequently in his master''s plots. In conflict, he uses his brute strength and his ability to control wild animals.', 1);

INSERT INTO character_quote (character_id, quote) VALUES (1, 'By the power of Grayskull... I have the power!'), 
(2, 'I am not nice, I am not kind, and I am not wonderful!﻿');

INSERT INTO cartoon_picture (picture_location, cartoon_id, location_id, character_id) VALUES ('/img/motu/masters-of-the-universe.jpg', 1, NULL, NULL), 
('/img/motu/eternia.jpg', NULL, 1, NULL), ('/img/motu/castle-grayskull.jpg', NULL, 2, NULL), ('/img/motu/royal-palace.jpg', NULL, 3, NULL), 
('/img/motu/snake-mountain.jpg', NULL, 4, NULL), ('/img/motu/he-man.jpg', NULL, NULL, 1), ('/img/motu/skeletor-attacking.jpg', NULL, NULL, 2), ('/img/motu/teela.png', NULL, NULL, 3), 
('/img/motu/man-at-arms.jpg', NULL, NULL, 4), ('/img/motu/sorceress.png', NULL, NULL, 5), ('/img/motu/mer-man-vs-he-man.jpg', NULL, NULL, 6), ('/img/motu/beast-man-vs-he-man.jpg', NULL, NULL, 7)
, ('/img/motu/battle-cat-and-he-man.jpg', NULL, NULL, 1), ('/img/motu/he-man-defending-with-sword.png', NULL, NULL, 1);

INSERT INTO gender (gender, description) VALUES ('M', 'male'), ('F', 'female'), ('U', 'unknown'), ('N','not applicable');

INSERT INTO character_demographic (gender, villain, character_id) VALUES ('M', FALSE, 1), ('M', TRUE, 2), ('F', FALSE, 3), ('M', FALSE, 4), 
('F', FALSE, 5), ('M', TRUE, 6), ('M', TRUE, 7);

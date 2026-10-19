// Original binary: sg/ec/u5xxX9nJM7go2dqb
package quick.module.misc;


import quick.setting.ActionSetting;
import quick.setting.ModeSetting;
import quick.setting.ToggleSetting;
import quick.module.combat.AttackAura;
import sg.ec.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

// $VF: Compiled from s
public class KillMessage extends ClientModule {
   private static final String field24210 = "KillMessage.json";
   private static final Gson field24211 = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
   private static String field24212 = "";
   private final ModeSetting field24213;
   private final ActionSetting field24214;
   private final ToggleSetting field24215;
   private final String[] field24216;
   LivingEntity field24217;
   private static final String field24218 = "Message style";
   private static final String field24219 = "Customer";
   private static final String field24220 = "Own";
   private static final String field24221 = "Customer";
   private static final String field24222 = "Open file";
   private static final String field24223 = "Bullying in a message";
   private static final String field24224 = "AHAHAHA, Fuck you idiot died like an eez";
   private static final String field24225 = "Fuck people buy normal cheat suck as you breathe";
   private static final String field24226 = "Suck plus lie";
   private static final String field24227 = "Whoa Demo Whoa Demo, I fell on my knees again";
   private static final String field24228 = "mamut rahal";
   private static final String field24229 = "alo ok cheat dada fuck you";
   private static final String field24230 = "Even the worms are leaving you, wash yourself";
   private static final String field24231 = "Your crits sound like farts in the wind.";
   private static final String field24232 = "Your attempts to hit me are a new kind of miss.";
   private static final String field24233 = "Go buy yourself a premium cheat, maybe you'll stop sucking.";
   private static final String field24234 = "Your tactic is to run and cry into voice chat.";
   private static final String field24235 = "You'd make a good training dummy.";
   private static final String field24236 = "Even a new player with a guide from 2012 will beat you up.";
   private static final String field24237 = "Did you think you had imbao? No, bro, your cheat is crap, getai Quick.";
   private static final String field24238 = "again the son of the pulpit fell on his knees";
   private static final String field24239 = "suicide is not a solution)))";
   private static final String field24240 = "Mom's baby boy died";
   private static final String field24241 = "Now I’ll make you a free-fall without elytra.";
   private static final String field24242 = "Well, you are a bichara, I put you on dick again (";
   private static final String field24243 = "Well, you're just the king of suckers, I've done you a dirty trick again";
   private static final String field24244 = "Fell again like a moose on ice";
   private static final String field24245 = "I came up with a nickname for your mom, dick cover, say the word if the nickname is ok";
   private static final String field24246 = "Well, you're a sucker, you fell again like a half-wit";
   private static final String field24247 = "You are so mediocre that even the bots play better than you!";
   private static final String field24248 = "you are top 1 in draining resources";
   private static final String field24249 = "Soap, all your attempts are nothing, go to hell, loser!";
   private static final String field24250 = "Elitras are for the strong, and you are a weakling who cannot master them!";
   private static final String field24251 = "It's like you're deliberately trying to look stupid.";
   private static final String field24252 = "Your attempts to fly on elytra are simply ridiculous, go outside!";
   private static final String field24253 = "ahahahahaha, the fagot fucked my fat dick with his fuck again. Go to sleep, motherfucker";
   private static final String field24254 = "Buy yourself some brains, otherwise you're a complete idiot";
   private static final String field24255 = "you're too dumb. Hwh it's a game of minds";
   private static final String field24256 = "What do you say to this? motherfucker!";
   private static final String field24257 = "a disabled person without arms fights better";
   private static final String field24258 = "I love killing people like you";
   private static final String field24259 = "You don't have enough resources, I need more!";
   private static final String field24260 = "you look like an unsofter, unless of course that's an insult to you";
   private static final String field24261 = "who is ez now hahaha";
   private static final String field24262 = "a crocodile is not a bird, a skibidi egg";
   private static final String field24263 = "Attsa fucked";
   private static final String field24264 = "wrote ez ended up at spawn";
   private static final String field24265 = "Quit the game if you suck it in seconds";
   private static final String field24266 = "bichugan died";
   private static final String field24267 = "Dick knows, not the enemy";
   private static final String field24268 = "Easy, son of a whore";
   private static final String field24269 = "Go find yourself a guy under the main, fagot";
   private static final String field24270 = "Have you forgotten the quack, cocksucker?";
   private static final String field24271 = "What are you doing here, Brawlstars has a new version, they added a new character, run faster!!!!!";
   private static final String field24272 = "Hey, sochnyara, you're messing with the public, you pissing fagot";
   private static final String field24273 = "Fucked up, what kind of day are you lying on, son of a creature?";
   private static final String field24274 = "Faggot, you are one of those people who take third place when playing 1v1";
   private static final String field24275 = "You are the reason abortion is legalized";
   private static final String field24276 = "ABUSE OF SKIT? NO FUCKING CRACK!";
   private static final String field24277 = "ez";
   private static final String field24278 = "too fucking easy";
   private static final String field24279 = "effortless";
   private static final String field24280 = "easiest kill of my life";
   private static final String field24281 = "retard blasted";
   private static final String field24282 = "cleans?";
   private static final String field24283 = "nice memesense retard";
   private static final String field24284 = "hello mind explaining what happened there";
   private static final String field24285 = "what about you wedges???";
   private static final String field24286 = "pissed on the mememaker lol";
   private static final String field24287 = "yuid Issue Hule is immediately visible here";
   private static final String field24288 = "the mother that kfg issue was sho gave birth to you";
   private static final String field24289 = "and you are also a Newcamich in life????";
   private static final String field24290 = "bitch don't embarrass yourself and showers lol";
   private static final String field24291 = "uid police pulled up open the door ubich";
   private static final String field24292 = "lol bottled";
   private static final String field24293 = "tranny holzed";
   private static final String field24294 = "but you go out there from Khrushchev and then jump out fucking";
   private static final String field24295 = "how to share a room with mom AHAHAHAHAHAH how did you accumulate a fucking";
   private static final String field24296 = "with 0.5x0.5m room fucking HAHAHAHAH you can not EVEN HANG YOURSELF THERE CAN NOT PLACE NO PHPHHPPH";
   private static final String field24297 = "better buy the superior hack!";
   private static final String field24298 = "for soap and rope, then the money is poor????";
   private static final String field24299 = "whatcha shootin at retard";
   private static final String field24300 = "the banners, legion, and.. AND FUCKING TYZH HERE FUCKING PHGAHAHAHAHAHAHAH)))))))";
   private static final String field24301 = "but what is the situation with Yuid?)))";
   private static final String field24302 = "fuck, this beggar dog whimpering here";
   private static final String field24303 = "i didn't even want to dirty my hands about you poor bitch";
   private static final String field24304 = "fuck you fell nicely on the bottle";
   private static final String field24305 = "sorry that there is no lubrication)))";
   private static final String field24306 = "hello this is an ambulance? here this situation OF poverty has fallen))) Oh and you are not HELPING with anything?? GOT IT I'LL GO THEN)))))))";
   private static final String field24307 = "jump out the window fucking beoberhoek user";
   private static final String field24308 = "hard with a memesense probably????";
   private static final String field24309 = "imagine losing at video games couldn't ever be me";
   private static final String field24310 = "but where are the opponents???";
   private static final String field24311 = "nice chromosome count you sell??";
   private static final String field24312 = "how you saved up on PC I don't even know )))))))))";
   private static final String field24313 = "iq more than two will be ok????";
   private static final String field24314 = "NIHAYA TAM NYUKAMYCHA OROSHILI URINE STREAM AHAHAHAHAHAHAHAHAHA";
   private static final String field24315 = "gave a yuid for a cheek check";
   private static final String field24316 = "nn4ik shat on";
   private static final String field24317 = "fucking put you down manka))))";
   private static final String field24318 = "and you have a family campaign to die)";
   private static final String field24319 = "did you fucking kill a faggot?";
   private static final String field24320 = "ciao peach ripen";
   private static final String field24321 = "respected in prison you will be a diver";
   private static final String field24322 = "say I'll talk badly I'll suck, I'll suck badly I'll suck";
   private static final String field24323 = "dolbayeb go slippers in a school chest collect";
   private static final String field24324 = "fuckin 'cho shoe fell off";
   private static final String field24325 = "brother to mother hello say hello";
   private static final String field24326 = "not an adversary";
   private static final String field24327 = "DEAD puffed up the poor";
   private static final String field24328 = "yuid Issue Hule is immediately visible here";
   private static final String field24329 = "the mother that kfg issue was sho gave birth to you";
   private static final String field24330 = "and you are also a Newcamich in life????";
   private static final String field24331 = "bitch don't embarrass yourself and showers lol";
   private static final String field24332 = "uid police pulled up open the door ubich";
   private static final String field24333 = "lol bottled";
   private static final String field24334 = "tranny holzed";
   private static final String field24335 = "but you go out there from Khrushchev and then jump out fucking";
   private static final String field24336 = "how to share a room with mom AHAHAHAHAHAH how did you accumulate a fucking";
   private static final String field24337 = "with 0.5x0.5m room fucking HAHAHAHAH you can not EVEN HANG YOURSELF THERE CAN NOT PLACE NO PHPHHPPH";
   private static final String field24338 = "better buy the superior hack!";
   private static final String field24339 = "for soap and rope, then the money is poor????";
   private static final String field24340 = "whatcha shootin at retard";
   private static final String field24341 = "the banners, legion, and.. AND FUCKING TYZH HERE FUCKING PHGAHAHAHAHAHAHAH)))))))";
   private static final String field24342 = "but what is the situation with Yuid?)))";
   private static final String field24343 = "fuck, this beggar dog whimpering here";
   private static final String field24344 = "i didn't even want to dirty my hands about you poor bitch";
   private static final String field24345 = "fuck you fell nicely on the bottle";
   private static final String field24346 = "sorry that there is no lubrication)))";
   private static final String field24347 = "hello this is an ambulance? here this situation OF poverty has fallen))) Oh and you are not HELPING with anything?? GOT IT I'LL GO THEN)))))))";
   private static final String field24348 = "jump out the window fucking beoberhoek user";
   private static final String field24349 = "hard with a memesense probably????";
   private static final String field24350 = "imagine losing at video games couldn't ever be me";
   private static final String field24351 = "but where are the opponents???";
   private static final String field24352 = "nice chromosome count you sell??";
   private static final String field24353 = "how you saved up on PC I don't even know )))))))))";
   private static final String field24354 = "iq more than two will be ok????";
   private static final String field24355 = "NIHAYA TAM NYUKAMYCHA OROSHILI URINE STREAM AHAHAHAHAHAHAHAHAHA";
   private static final String field24356 = "gave a yuid for a cheek check";
   private static final String field24357 = "nn4ik shat on";
   private static final String field24358 = "fucking put you down manka))))";
   private static final String field24359 = "and you have a family campaign to die)";
   private static final String field24360 = "did you fucking kill a faggot?";
   private static final String field24361 = "ciao peach ripen";
   private static final String field24362 = "respected in prison you will be a diver";
   private static final String field24363 = "say I'll talk badly I'll suck, I'll suck badly I'll suck";
   private static final String field24364 = "dolbayeb go slippers in a school chest collect";
   private static final String field24365 = "fuckin 'cho shoe fell off";
   private static final String field24366 = "brother to mother hello say hello";
   private static final String field24367 = "not an adversary";
   private static final String field24368 = "are you a clown???";
   private static final String field24369 = "i pissed on you (";
   private static final String field24370 = "what did you fly away from there?";
   private static final String field24371 = "fucking dick fell down, but he took it in his bodysuit and fucked it up anyway";
   private static final String field24372 = "DEAD levai from minx (";
   private static final String field24373 = "before linking shoe";
   private static final String field24374 = "poverty the stupidest play learn";
   private static final String field24375 = "omitted son of a creature";
   private static final String field24376 = "the beggar flew away";
   private static final String field24377 = "DEAD puffed up the poor";
   private static final String field24378 = "you can immediately see the cfg issue mb config from me?";
   private static final String field24379 = "animal addon download otherwise you fall";
   private static final String field24380 = "Armenian Queen";
   private static final String field24381 = "bitch don't disgrace yourself and showers";
   private static final String field24382 = "the fucking slipper flew away";
   private static final String field24383 = "one fell off the jersey";
   private static final String field24384 = "why are you playing here brainless";
   private static final String field24385 = "go drink some kumiss next";
   private static final String field24386 = "quit sochnyar";
   private static final String field24387 = "Do you have money for soap and rope????";
   private static final String field24388 = "fresh wild boar";
   private static final String field24389 = "before the connection on the jump hog";
   private static final String field24390 = "tell your mom to buy crackers, motherfucker";
   private static final String field24391 = "fuck you sat down beautifully on the bottle, should I give you a viola?";
   private static final String field24392 = "sorry for the lack of lubrication)";
   private static final String field24393 = "Hello is this an ambulance? There’s a situation here: the guy who fell needs an ambulance)";
   private static final String field24394 = "hello you're cheating your map you fool wake up";
   private static final String field24395 = "hard with crack????";
   private static final String field24396 = "SECOND-CLASS FUCKING PROMOTER OTKIS";
   private static final String field24397 = "but where are the opponents???";
   private static final String field24398 = "do you play legit?";
   private static final String field24399 = "THE PARTY FUCK JUMPED WHERE IT FLY";
   private static final String field24400 = "Where are you, a victim of goat abortion?";
   private static final String field24401 = "iq?";
   private static final String field24402 = "where are you going son of a phantom stationary special isolated metal-forming mechanism";
   private static final String field24403 = "nice antiaim, you sell?";
   private static final String field24404 = "you pay for that?";
   private static final String field24405 = "consider suicide";
   private static final String field24406 = "bro are u clean?";
   private static final String field24407 = "another retard blasted";
   private static final String field24408 = "paster abandoned the match and received a 7 day competitive matchmaking cooldown";
   private static final String field24409 = "fuck this is fucked up what are some beggars with memsense burping fucking";
   private static final String field24410 = "go to the factory";
   private static final String field24411 = "JAJAJAJJAJA NICE RAINBOWPASTE ROFL";
   private static final String field24412 = "140er????";
   private static final String field24413 = "get good get quick client";
   private static final String field24414 = "1 but all you need to fix your problems is a rope and a chair you ugly shit";
   private static final String field24415 = "who (kto) are you (nn4ik) wattafak mens???????";
   private static final String field24416 = "must be an uid issue";
   private static final String field24417 = "fuck you got carried away";
   private static final String field24418 = "Refund please";
   private static final String field24419 = "on the bottle Rusak";
   private static final String field24420 = "and you (you) sir actually who (who)?";
   private static final String field24421 = "fucking pussy, can you reflash???";
   private static final String field24422 = "how is life with rupasta??????";
   private static final String field24423 = "stop spending your lunch money on shitpastes retard";
   private static final String field24424 = "damn I just passed by and you already fell AUU POOR ARE YOU ALL GOOD????????????))))))";
   private static final String field24425 = "oh the beggar fell, we'll call an ambulance now";
   private static final String field24426 = "oh and who (who) are you (you) such a wotzefak man))))))";
   private static final String field24427 = "thats going in my media compilation right there get shamed retard rofl";
   private static final String field24428 = "imagine the only thing you eat being bullets man being a thirdworlder must suck rofl";
   private static final String field24429 = "so fucking ez";
   private static final String field24430 = "botkick";
   private static final String field24431 = "where the enemies at????";
   private static final String field24432 = "Ale Nice fell beggar LOOOOOOOL";
   private static final String field24433 = "Are you okay there????????????? why the hell are you poor I ask PHAHAHAHAHAHA";
   private static final String field24434 = "It’s a pity, of course, that you have to play against a beggar)))";
   private static final String field24435 = "Even a spin won't help you, fuck you even live";
   private static final String field24436 = "can you populate an extra chromosome???";
   private static final String field24437 = "then teach me how to suck like that on xvx";
   private static final String field24438 = "when I didn’t save up for hormones and stockings)))))))))))))";
   private static final String field24439 = "How's life on mom's pension???????";
   private static final String field24440 = "Well, I seem to have registered botkik in the console, but it didn’t kick you there, is that a bug??)))))))))";
   private static final String field24441 = "crack user down, fuck the factory";
   private static final String field24442 = "I don’t understand, you’re so fat because you eat a lot of fat every day???? Find a normal job, maybe you’ll have enough money for food))))))))))))";
   private static final String field24443 = "shit in your roteshnik, check it";
   private static final String field24444 = "no fucking way you were there, like a plane flew off HAHAHAHAHAHAHAHAHAHAHAHA";
   private static final String field24445 = "FUCKING BEGGAR DICK DICK AND YOU ARE NOT HOW CAN YOU BE LIKE THIS?????? EXPLAIN YOUR FUCK";
   private static final String field24446 = "hold your umbrella, you've been pissed";
   private static final String field24447 = "Hold your penis and take off!";
   private static final String field24448 = "too juicy for Djamic.technologies";
   private static final String field24449 = "fucking cockroach with a mustache";
   private static final String field24450 = "motherfucker";
   private static final String field24451 = "fuck you fell, get up and go to the factory";
   private static final String field24452 = "unseasonably rustling fraer";
   private static final String field24453 = "EASY SMACK FUCKING";
   private static final String field24454 = "fuck off don't disgrace yourself";
   private static final String field24455 = "AHAHA FAGOT FALLED FROM VANGWAY HAHAHA ONLY BAIM IN A BODY SWEATS ON FD AHA";
   private static final String field24456 = "AHAHA THE CHMOSHNIK FLYED AWAY";
   private static final String field24457 = "1 MOTHER SORRY";
   private static final String field24458 = "go to the factory";
   private static final String field24459 = "no no he fell to his knees again";
   private static final String field24460 = "get up fucked up, school tomorrow";
   private static final String field24461 = "get good get fuck you";
   private static final String field24462 = "by SANCHEZj hvh boss";
   private static final String field24463 = "well no why does he lie down when I pass";
   private static final String field24464 = "your nickname is adeo?";
   private static final String field24465 = "Guy, can you come up with a nickname?";
   private static final String field24466 = "so stupid (((";
   private static final String field24467 = "stop getting xs, better take your hand and go jerk off";
   private static final String field24468 = "no no this cracker is so funny";
   private static final String field24469 = "1 son of a whore";
   private static final String field24470 = "1 fucked your mother";
   private static final String field24471 = "buy my cfg clown";
   private static final String field24472 = "did you hit your head on the tile?";
   private static final String field24473 = "motherfucker";
   private static final String field24474 = "cocksucker keep coming to me";
   private static final String field24475 = "call me again for backup";
   private static final String field24476 = "not an adversary";
   private static final String field24477 = "nickname name Dorian(";
   private static final String field24478 = "iq ?";
   private static final String field24479 = "fell = minus mother";
   private static final String field24480 = "don't write to me";
   private static final String field24481 = "It’s a pity of course that I’m playing against you, but what can I do?";
   private static final String field24482 = "Adinichka fell";
   private static final String field24483 = "son, why do you need this?";
   private static final String field24484 = "How long have you been putting it in your mouth?";
   private static final String field24485 = "can I";
   private static final String field24486 = "you fucked me up so much, it's already raining";
   private static final String field24487 = "life teaches nothing (";
   private static final String field24488 = "I don’t understand, are you so fat because you eat a lot of fat every day?";
   private static final String field24489 = "The fat man can't even walk calmly";
   private static final String field24490 = "are you a clown???";
   private static final String field24491 = "i pissed on you (";
   private static final String field24492 = "what did you fly away from there?";
   private static final String field24493 = "fucking dick fell down, but he took it in his bodysuit and fucked it up anyway";
   private static final String field24494 = "DEAD levai from minx (";
   private static final String field24495 = "before linking shoe";
   private static final String field24496 = "poverty the stupidest play learn";
   private static final String field24497 = "omitted son of a creature";
   private static final String field24498 = "the beggar flew away";
   private static final String field24499 = "take the cooldown and let your team surr retard";
   private static final String field24500 = "go take some estrogen tranny";
   private static final String field24501 = "uid police here present your user identification number right now";
   private static final String field24502 = "the beggar flew away";
   private static final String field24503 = "why did you fly off there?";
   private static final String field24504 = "lmao ur so ugly irl like bro doesnt it hurt to live like that, btw you know you can just end it all";
   private static final String field24505 = "Why the hell are you falling? Go buy it from Kislyashi KFG for crack";
   private static final String field24506 = "I KNOCKED YOUR MOTHER'S TEETH OUT WITH A CUCKSUCK";
   private static final String field24507 = "Some people get paid for oral sex, but you do it for free";
   private static final String field24508 = "My friend, with this level of play, go kill yourself against the wall, just like your dad";
   private static final String field24509 = "Humiliated, son of a pissing dog";
   private static final String field24510 = "Son of a fagot, he stabbed your mother to death";
   private static final String field24511 = "got a dick on the nose";
   private static final String field24512 = "on your knees on your knees";
   private static final String field24513 = "was shaken to 0";
   private static final String field24514 = "how did you die??)";
   private static final String field24515 = "your dad is Chinese";
   private static final String field24516 = "Well, who killed you, brother?)";
   private static final String field24517 = "take your grandma's pension again so you can add it to the cubes";
   private static final String field24518 = "your father was weak like you";
   private static final String field24519 = "There is no feeling stronger than laziness on your knees on your knees";
   private static final String field24520 = "It's nice to have a grandfather with you!";
   private static final String field24521 = "HAHFAHFA well you fucking freak died without a chance";
   private static final String field24522 = "What's wrong with grandma, he died like a fool";
   private static final String field24523 = "Sucked a delicious dick";
   private static final String field24524 = "Minus a pussy sucker";
   private static final String field24525 = "Fucking sucker, leaked like a mediocrity";
   private static final String field24526 = "Fuck man, get out of the game";
   private static final String field24527 = "start making excuses";
   private static final String field24528 = "why are you naked, I didn’t want pvp with you";
   private static final String field24529 = "It's probably all because of the ping, so what you're saying?";
   private static final String field24530 = "call a moderator and let him ban me(((";
   private static final String field24531 = "just don't get depressed";
   private static final String field24532 = "got knocked up for the first time and sucked";
   private static final String field24533 = "What's wrong with the face???";
   private static final String field24534 = "Oops, the little fucker is broken";
   private static final String field24535 = "Beat all the balls in the clotcha";
   private static final String field24536 = "damn, you're a complete fucking idiot of course HAHAHAHA";
   private static final String field24537 = "Bitch getni ok cheat and don't suck like a whore";
   private static final String field24538 = "Damn, you cheater, how do you even live without cheats? Bastard without a brain!";
   private static final String field24539 = "Kill Message";
   private static final String field24540 = "KillMessage.json";
   private static final String field24541 = "{\n  \"instruction\": [\n    \"Что-бы добавить свое сообщение напишите through строку так\",\n    \"\",\n    \"Пример 'name' как так можно просто!,\",\n    \"Пример hello 'name' я тебя убил,\",\n    \"\",\n    \"Следущее сообщение и так through запятую\"\n  ],\n  \"messages\": [\n    \"Пример 'name' как так можно просто!\",\n    \"Пример hello 'name' я тебя убил\"\n  ]\n}\n";
   private static final String field24542 = "instruction";
   private static final String field24543 = "\r\n";
   private static final String field24544 = "\n";
   private static final String field24545 = "\n";
   private static final String field24546 = "instruction";
   private static final String field24547 = "\\u0027";
   private static final String field24548 = "'name'";
   private static final String field24549 = "{name}";
   private static final String field24550 = "messages";
   private static final String field24551 = "Own";
   private static final String field24552 = "Customer";
   private static final String field24553 = "Own";
   private static final String field24554 = "notepad.exe";

   private String method10905(String var1) {
      String[] var2 = this.method10911();
      if (var2.length == 0) {
         return "";
      }

      String sx = var2[RandomUtils.method10453(var2.length)];
      return method10913(sx, var1);
   }

   public KillMessage() {
      super(ModuleCategory.Misc, ModuleSubCategory.Servers);
      this.field24213 = new ModeSetting(field24218).setModes(field24219, field24220).setDefaultValue(field24221).setContainer(this);
      this.field24214 = new ActionSetting(field24222).setDefaultValue(() -> {
         try {
            this.method10908();
            Runtime.getRuntime().exec(new String[]{field24554, this.method10909().toAbsolutePath().toString()});
         } catch (IOException var2) {
         }
      }).setVisibleSupplier(() -> this.field24213.isMode(field24553)).setContainer(this);
      this.field24215 = new ToggleSetting(field24223).setValue(false).setVisibleSupplier(() -> this.field24213.isMode(field24552)).setContainer(this);
      this.field24216 = new String[]{
         field24224,
         field24225,
         field24226,
         field24227,
         field24228,
         field24229,
         field24230,
         field24231,
         field24232,
         field24233,
         field24234,
         field24235,
         field24236,
         field24237,
         field24238,
         field24239,
         field24240,
         field24241,
         field24242,
         field24243,
         field24244,
         field24245,
         field24246,
         field24247,
         field24248,
         field24249,
         field24250,
         field24251,
         field24252,
         field24253,
         field24254,
         field24255,
         field24256,
         field24257,
         field24258,
         field24259,
         field24260,
         field24261,
         field24262,
         field24263,
         field24264,
         field24265,
         field24266,
         field24267,
         field24268,
         field24269,
         field24270,
         field24271,
         field24272,
         field24273,
         field24274,
         field24275,
         field24276,
         field24277,
         field24278,
         field24279,
         field24280,
         field24281,
         field24282,
         field24283,
         field24284,
         field24285,
         field24286,
         field24287,
         field24288,
         field24289,
         field24290,
         field24291,
         field24292,
         field24293,
         field24294,
         field24295,
         field24296,
         field24297,
         field24298,
         field24299,
         field24300,
         field24301,
         field24302,
         field24303,
         field24304,
         field24305,
         field24306,
         field24307,
         field24308,
         field24309,
         field24310,
         field24311,
         field24312,
         field24313,
         field24314,
         field24315,
         field24316,
         field24317,
         field24318,
         field24319,
         field24320,
         field24321,
         field24322,
         field24323,
         field24324,
         field24325,
         field24326,
         field24327,
         field24328,
         field24329,
         field24330,
         field24331,
         field24332,
         field24333,
         field24334,
         field24335,
         field24336,
         field24337,
         field24338,
         field24339,
         field24340,
         field24341,
         field24342,
         field24343,
         field24344,
         field24345,
         field24346,
         field24347,
         field24348,
         field24349,
         field24350,
         field24351,
         field24352,
         field24353,
         field24354,
         field24355,
         field24356,
         field24357,
         field24358,
         field24359,
         field24360,
         field24361,
         field24362,
         field24363,
         field24364,
         field24365,
         field24366,
         field24367,
         field24368,
         field24369,
         field24370,
         field24371,
         field24372,
         field24373,
         field24374,
         field24375,
         field24376,
         field24377,
         field24378,
         field24379,
         field24380,
         field24381,
         field24382,
         field24383,
         field24384,
         field24385,
         field24386,
         field24387,
         field24388,
         field24389,
         field24390,
         field24391,
         field24392,
         field24393,
         field24394,
         field24395,
         field24396,
         field24397,
         field24398,
         field24399,
         field24400,
         field24401,
         field24402,
         field24403,
         field24404,
         field24405,
         field24406,
         field24407,
         field24408,
         field24409,
         field24410,
         field24411,
         field24412,
         field24413,
         field24414,
         field24415,
         field24416,
         field24417,
         field24418,
         field24419,
         field24420,
         field24421,
         field24422,
         field24423,
         field24424,
         field24425,
         field24426,
         field24427,
         field24428,
         field24429,
         field24430,
         field24431,
         field24432,
         field24433,
         field24434,
         field24435,
         field24436,
         field24437,
         field24438,
         field24439,
         field24440,
         field24441,
         field24442,
         field24443,
         field24444,
         field24445,
         field24446,
         field24447,
         field24448,
         field24449,
         field24450,
         field24451,
         field24452,
         field24453,
         field24454,
         field24455,
         field24456,
         field24457,
         field24458,
         field24459,
         field24460,
         field24461,
         field24462,
         field24463,
         field24464,
         field24465,
         field24466,
         field24467,
         field24468,
         field24469,
         field24470,
         field24471,
         field24472,
         field24473,
         field24474,
         field24475,
         field24476,
         field24477,
         field24478,
         field24479,
         field24480,
         field24481,
         field24482,
         field24483,
         field24484,
         field24485,
         field24486,
         field24487,
         field24488,
         field24489,
         field24490,
         field24491,
         field24492,
         field24493,
         field24494,
         field24495,
         field24496,
         field24497,
         field24498,
         field24499,
         field24500,
         field24501,
         field24502,
         field24503,
         field24504,
         field24505,
         field24506,
         field24507,
         field24508,
         field24509,
         field24510,
         field24511,
         field24512,
         field24513,
         field24514,
         field24515,
         field24516,
         field24517,
         field24518,
         field24519,
         field24520,
         field24521,
         field24522,
         field24523,
         field24524,
         field24525,
         field24526,
         field24527,
         field24528,
         field24529,
         field24530,
         field24531,
         field24532,
         field24533,
         field24534,
         field24535,
         field24536,
         field24537,
         field24538
      };
      this.field24217 = null;
      this.setName(field24539);
   }

   @Subscribe
   public void method10906(PlayerUpdateEvent var1) {
      if (AttackAura.field00785 != null) {
         this.field24217 = AttackAura.field00785;
      }
   }

   private void method10908() throws IOException {
      Path var1 = this.method10909();
      Files.createDirectories(var1.getParent());
      if (Files.notExists(var1)) {
         Files.writeString(var1, field24541, StandardCharsets.UTF_8);
      } else {
         this.method10912(var1);
         this.method10914(var1);
      }
   }

   private Path method10909() {
      return new File(ClientEnvironment.field07577, field24540).toPath();
   }

   private String[] method10911() {
      try {
         this.method10908();
         String var1 = Files.readString(this.method10909(), StandardCharsets.UTF_8);
         JsonElement var2 = JsonParser.parseString(var1);
         if (!var2.isJsonObject()) {
            return new String[0];
         }

         JsonArray var3 = var2.getAsJsonObject().getAsJsonArray(field24550);
         if (var3 == null) {
            return new String[0];
         }

         ArrayList<String> var4 = new ArrayList<>();

         for (JsonElement var6 : var3) {
            if (var6.isJsonPrimitive() && var6.getAsJsonPrimitive().isString()) {
               String var7 = var6.getAsString().trim();
               if (!var7.isEmpty()) {
                  var4.add(var7);
               }
            }
         }

         return var4.toArray(new String[0]);
      } catch (Exception var8) {
         return new String[0];
      }
   }

   private void method10912(Path var1) {
      try {
         String var2 = Files.readString(var1, StandardCharsets.UTF_8);
         JsonElement var3 = JsonParser.parseString(var2);
         if (!var3.isJsonObject()) {
            return;
         }

         JsonObject var4 = var3.getAsJsonObject();
         JsonElement var5 = var4.get(field24542);
         if (var5 == null || !var5.isJsonPrimitive() || !var5.getAsJsonPrimitive().isString()) {
            return;
         }

         String var6 = var5.getAsString().replace(field24543, field24544);
         JsonArray var7 = new JsonArray();

         for (String var11 : var6.split(field24545, -1)) {
            var7.add(var11);
         }

         var4.add(field24546, var7);
         Files.writeString(var1, field24211.toJson(var4), StandardCharsets.UTF_8);
      } catch (Exception var12) {
      }
   }

   private static String method10913(String var0, String var1) {
      return var0.replace(field24548, var1).replace(field24549, var1);
   }

   private void method10914(Path var1) {
      try {
         String var2 = Files.readString(var1, StandardCharsets.UTF_8);
         if (!var2.contains(field24547)) {
            return;
         }

         JsonElement var3 = JsonParser.parseString(var2);
         Files.writeString(var1, field24211.toJson(var3), StandardCharsets.UTF_8);
      } catch (Exception var4) {
      }
   }

   @Subscribe
   private void method10915(EntityDeathEvent var1) {
      Entity var2 = var1.method11303();
      if (!(var2 instanceof LocalPlayer)) {
         if (mc.player != null && mc.level != null) {
            if (this.field24217 != null && var2 == this.field24217) {
               this.field24217 = null;
               String var3 = var2.getName().getString();
               String var4;
               if (this.field24213.isMode(field24551)) {
                  var4 = this.method10905(var3);
               } else if (this.field24215.getValue()) {
                  var4 = "-ezz " + var3 + " " + this.field24216[RandomUtils.method10453(this.field24216.length)];
               } else {
                  var4 = "-ezz " + var3;
               }

               ChatTextUtils.sendQueuedCommand(("! " + var4).trim());
            }
         }
      }
   }
}

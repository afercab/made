/*
 * Copyright 2013 Rubén Héctor García <raiben@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.velonuboso.basicmade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 *
 * @author Ruben
 */
public class MadeAgent {

    public static int NUMBER_OF_FEATURES = 12;
    private static final int FEATURE_BITE = 0; // attack
    private static final int FEATURE_FUR = 1; // defense
    private static final int FEATURE_PROFILE_VARIANCE = 2;
    private static final int FEATURE_HEALTH = 3;
    private static final int FEATURE_LIFE = 4;
    private static final int FEATURE_SMELL = 5;
    private static final int FEATURE_METHABOLISM = 6;
    private static final int FEATURE_HUNGRY_LEVEL = 7;
    private static final int FEATURE_PROCREATION = 8;
    private static final int FEATURE_ENJOYABLE = 9;
    private static final int FEATURE_AGE_TO_BE_ADULT = 10;
    private static final int FEATURE_PREGNANCY_TIME = 11;


    private int id;
    private Gender gender;
    private int profile;
    private String name;
    private String surname;
    private String nickname;
    private MadeEnvironment env;
    private Random r;
    private int days = -1;

    private int maxDays = -1;
    private double profileVariance;
    private int energy;
    private int maxEnergy;
    private int smell;
    private int nutrition;
    private int bite;
    private int fur;
    private double hungryLevel;
    private double procreation;
    private double enjoyable;
    private double personality;
    
    private int x = -1;
    private int y = -1;
    private boolean logSheet;
    private boolean logSummary;
    private StringBuffer strb;
    private HashSet<String> labels;
    private int ageToBeAdult;

    // States
    private boolean alive = false;
    private int pregnancy = 0; // 0 = not-pregnant; otherwise, the days to have a child
    private boolean child = true;
    private MadeAgent inLoveWith;

    MadeEvaluator e = MadeEvaluator.getInstance();

    public MadeAgent(int id, int date, Gender gender, int profile, String name,
            String surname, String nickname, MadeEnvironment env,
            Random r, boolean logSheet) {

        profileVariance = env.getVal(profile, FEATURE_PROFILE_VARIANCE);
        this.logSheet = logSheet;

        this.id = id;
        this.gender = gender;
        this.profile = profile;
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.env = env;
        this.r = r;
        alive = true;


        days = 0;
        // A Rattus rattus Norvegicus can live 12 months (360 days)

        int BASE_DAYS = e.getProperty(e.BASE_DAYS);
        maxDays = (int) (BASE_DAYS
                + (BASE_DAYS * env.getVal(profile, FEATURE_LIFE))
                + ((BASE_DAYS * env.getVal(profile, FEATURE_LIFE))
                * (((r.nextDouble() * 2) - 1) * profileVariance)));

        // About one week without eating
        int BASE_ENERGY = e.getProperty(e.BASE_ENERGY);
        maxEnergy = (int) (BASE_ENERGY
                + (BASE_ENERGY * env.getVal(profile, FEATURE_HEALTH))
                + ((BASE_ENERGY * env.getVal(profile, FEATURE_HEALTH))
                * (((r.nextDouble() * 2) - 1) * profileVariance)));
        energy = maxEnergy;

        // between 1 and 15 cells to search food
        int BASE_SMELL = e.getProperty(e.BASE_SMELL);
        smell = (int) (BASE_SMELL
                + (BASE_SMELL * env.getVal(profile, FEATURE_SMELL))
                + ((BASE_SMELL * env.getVal(profile, FEATURE_SMELL))
                * (((r.nextDouble() * 2) - 1) * profileVariance)));

        int BASE_NUTRITION = e.getProperty(e.BASE_NUTRITION);
        nutrition = (int) (BASE_NUTRITION
                + (BASE_NUTRITION * env.getVal(profile, FEATURE_METHABOLISM))
                + ((BASE_NUTRITION * env.getVal(profile, FEATURE_METHABOLISM))
                * (((r.nextDouble() * 2) - 1) * profileVariance)));

        hungryLevel = env.getVal(profile, FEATURE_HUNGRY_LEVEL);

        int BASE_BITE = e.getProperty(e.BASE_BITE);
        bite = (int) (BASE_BITE
                + (BASE_BITE * env.getVal(profile, FEATURE_BITE))
                + ((BASE_BITE * env.getVal(profile, FEATURE_BITE))
                * (((r.nextDouble() * 2) - 1) * profileVariance)));

        int BASE_FUR = e.getProperty(e.BASE_FUR);
        fur = (int) (BASE_FUR
                + (BASE_FUR * env.getVal(profile, FEATURE_FUR))
                + ((BASE_FUR * env.getVal(profile, FEATURE_FUR))
                * (((r.nextDouble() * 2) - 1) * profileVariance)));

        procreation = env.getVal(profile, FEATURE_PROCREATION);

        enjoyable = env.getVal(profile, FEATURE_ENJOYABLE);

        personality = r.nextDouble();

        if (gender == gender.FEMALE){
            int BASE_AGE_TO_BE_ADULT_FEMALE = e.getProperty(e.BASE_AGE_TO_BE_ADULT_FEMALE);
            ageToBeAdult = (int) (BASE_AGE_TO_BE_ADULT_FEMALE
                + (BASE_AGE_TO_BE_ADULT_FEMALE * env.getVal(profile, FEATURE_AGE_TO_BE_ADULT))
                + ((BASE_AGE_TO_BE_ADULT_FEMALE * env.getVal(profile, FEATURE_AGE_TO_BE_ADULT))
                * (((r.nextDouble() * 2) - 1) * profileVariance)));
        }
        if (logSheet) {
            strb = new StringBuffer();
            addline(days, MadeState.BORN+ " " + this.gender);
        }

        labels = new HashSet<String>();
    }

    boolean isAlive() {
        return alive;
    }

    void justLive() {
        days++;
        energy--;

        if (pregnancy > 0){
            pregnancy --;
            if (pregnancy == 0){
                env.newAgents(this, inLoveWith, 10);
            }
        }

        if (days > maxDays || energy <= 0) {
            alive = false;
            addline(days, MadeState.DIE +" ");
        } else {
            if (energy < maxEnergy * hungryLevel + (1 - 2 * r.nextDouble())) {
                addline(days, MadeState.HUNGRY +" " + energy);
                Position p = env.findFreeFood(this, smell);
                if (p != null) {
                    addline(days, MadeState.EAT +" " + nutrition);
                    env.moveAgent(this, p);
                    env.eatFood(p);
                    energy += nutrition;
                } else {
                    p = env.findFoodWithAgent(this, smell);
                    if (p != null) {
                        MadeAgent target = env.getAgent(p);
                        if (this.getBite() > target.getFur()) {
                            target.nudge(this);
                            addline(days, MadeState.NUDGE_OK +" " + target.id);
                            addline(days, MadeState.EAT +" " + nutrition);
                            env.moveAgent(this, p);
                            env.eatFood(p);
                            energy += nutrition;
                        } else {
                            target.defended(this);
                            addline(days, MadeState.NUDGE_FAILED+" " + target.id);
                        }
                    }
                }
            } else {
                if (this.gender == gender.FEMALE){
                    if (pregnancy == 0){

                        if (days >= ageToBeAdult && inLoveWith == null && r.nextDouble()<=procreation){

                            addline(days, MadeState.LOOK_FOR_PARTNER.toString());

                            ArrayList<MadeAgent> partners = env.getAgentsAround(this, smell);
                            MadeAgent partner = null;
                            int t = 0;
                            while (t<partners.size() && partner == null){
                                MadeAgent madeAgent = partners.get(t);
                                if (madeAgent.likes(this) && this.likes(madeAgent)){
                                    partner = madeAgent;
                                }
                                t++;
                            }

                            int BASE_PREGNANCY_TIME = e.getProperty(e.BASE_PREGNANCY_TIME);
                            if (partner!=null){

                                addline(days, MadeState.PARTNER_FOUND + " " + partner.getId());
                                partner.addline(days,  MadeState.PARTNER_FOUND + " " + this.getId());

                                this.inLoveWith = partner;
                                partner.inLoveWith = this;
                                this.pregnancy = (int)
                                    (BASE_PREGNANCY_TIME
                                    + (BASE_PREGNANCY_TIME * env.getVal(profile, FEATURE_PREGNANCY_TIME))
                                    + ((BASE_PREGNANCY_TIME * env.getVal(profile, FEATURE_PREGNANCY_TIME))
                                    * (((r.nextDouble() * 2) - 1) * profileVariance)));

                                addline(days, MadeState.PREGNANT + " " + partner.getId());
                            }
                        }
                    }
                }else{
                    Position p = env.getFreePosition(this, smell);
                    if (p != null) {
                        env.moveAgent(this, p);
                        addline(days, MadeState.MOVE +" " + p.x + " " + p.y);
                    }
                }
            }

        }
    }

    public String getStringLog() {
        if (strb == null) {
            return "";
        } else {
            return strb.toString();
        }
    }


    public void addline(int days, String action) {
        if (strb == null) {
            strb = new StringBuffer();
        }
        strb.append(days + ":" + "@" +action + "\n");
    }

    private void nudge(MadeAgent source) {
        ArrayList<Position> arr = new ArrayList<Position>();
        for (int a = x - 1; a < x + 2; a += 2) {
            if (a > 0 && a < e.getProperty(e.MAP_DIMENSION)) {
                for (int b = y - 1; b < y + 2; b += 2) {
                    if (b > 0 && b < e.getProperty(e.MAP_DIMENSION)) {
                        Position p = new Position();
                        p.x = a;
                        p.y = b;
                        arr.add(p);
                    }
                }
            }
        }
        if (arr.size() > 0) {
            Position p = null;
            if (arr.size() == 1) {
                p = arr.get(0);
            } else {
                p = arr.get(r.nextInt(arr.size() - 1));
            }
            env.moveAgent(this, p);
            this.energy--;
            addline(days, MadeState.NUDGED +" " + source.id);
        } else {
            alive = false;
            addline(days, MadeState.DIE +" ");
        }
    }

    private void defended(MadeAgent source) {
        addline(days, MadeState.DEFENDED +" " + source.id);
    }

    private boolean likes(MadeAgent target) {
        double minPer = Math.min(this.personality,target.personality);
        double maxPer = Math.max(this.personality,target.personality);
        double minDistance1 = Math.abs(maxPer-minPer);
        double minDistance2 = Math.abs(minPer+10-maxPer);
        double distance = Math.min(minDistance1, minDistance2);

        // max distance = 5, so normalizing consist on dividing between 5
        double distanceRelation = distance / 5.0;
        double rel = ((this.enjoyable+target.enjoyable)/2.0) / distanceRelation;
        return rel>=1;
    }


    // -------------------------------------------------------------------------

    public String getSheet() {
        String ret = "";
        ret += "------------------------------------" + "\n";
        ret += "Name: " + name + " (" + nickname + ") " + surname + "\n";
        ret += "Id: " + id + "\n";
        ret += "Gender: " + gender + "\n";
        ret += "Variance: " + profileVariance + "\n";
        ret += "Profile: " + profile + "\n";
        ret += "Alive: " + alive + "\n";
        ret += "Days: " + days + "\n";
        ret += "MaxDays: " + maxDays + "\n";
        ret += "Energy: " + energy + "\n";
        ret += "Maximum energy: " + maxEnergy + "\n";
        ret += "Smell: " + smell + "\n";
        ret += "Nutrition: " + nutrition + "\n";
        ret += "Hungry level: " + hungryLevel + "\n";
        ret += "Bite: " + bite + "\n";
        ret += "Fur: " + fur + "\n";
        ret += "Procreation: " + procreation + "\n";
        ret += "Character: " + personality + "\n";

        ret += "Labels: " + "{";
        for (String key : labels) {
            ret += "key ";
        }
        ret += "}\n";

        return ret;
    }

    //--------------------------------------------------------------------------

    // getters and setters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPosition(Position p) {
        x = p.x;
        y = p.y;
    }

    public Position getPosition() {
        Position p = new Position();
        p.x = x;
        p.y = y;
        return p;
    }

    public int getDays() {
        return days;
    }

    int getId() {
        return id;
    }

    private double getBite() {
        return bite;
    }

    private double getFur() {
        return fur;
    }

    void addLabel(String label) {
        labels.add(label);
    }

    public int getProfile() {
        return profile;
    }

    public String getSurname() {
        return surname;
    }

    public String getNickname() {
        return nickname;
    }

    public HashSet<String> getLabels() {
        return labels;
    }
}

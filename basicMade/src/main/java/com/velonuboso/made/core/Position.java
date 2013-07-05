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

package com.velonuboso.made.core;

/**
 *
 * @author Ruben
 */
public class Position {
    
    public static Position NULL_POSITION;
    static{
        NULL_POSITION = new Position(-1,-1);
    }
    
    public int x, y;

    public Position() {
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    
}
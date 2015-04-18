/*
 * Copyright (C) 2015 rhgarcia
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.velonuboso.made.core;

import com.velonuboso.made.interfaces.ICell;
import com.velonuboso.made.interfaces.ICharacter;
import com.velonuboso.made.interfaces.IPosition;
import com.velonuboso.made.interfaces.Terrain;

/**
 *
 * @author Rubén Héctor García (raiben@gmail.com)
 */
public class Cell implements ICell {

    ICharacter character = null;
    IPosition position = null;
    Terrain terrain = null;

    public Cell() {
        position = ObjectFactory.createObject(IPosition.class);
        position.setCoords(-1, -1);
    }

    @Override
    public ICharacter getCharacter() {
        return character;
    }

    @Override
    public IPosition getPosition() {
        return position;
    }

    @Override
    public Terrain getTerrain() {
        return terrain;
    }

    @Override
    public void setCharacter(ICharacter newCharacter) {
        character = newCharacter;
    }

    @Override
    public void setTerrain(Terrain newTerrain) {
        terrain = newTerrain;
    }
}

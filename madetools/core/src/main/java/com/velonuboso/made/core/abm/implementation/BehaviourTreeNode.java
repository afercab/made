/*
 * Copyright (C) 2015 Rubén Héctor García (raiben@gmail.com)
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
package com.velonuboso.made.core.abm.implementation;

import com.velonuboso.made.core.abm.api.IBehaviourTree;
import com.velonuboso.made.core.abm.api.IBehaviourTreeNode;
import com.velonuboso.made.core.abm.api.ICharacter;
import com.velonuboso.made.core.abm.api.IEventsWriter;
import com.velonuboso.made.core.abm.api.IMap;
import com.velonuboso.made.core.abm.entity.BehaviourTreeNodeStatus;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 *
 * @author Rubén Héctor García (raiben@gmail.com)
 */
public class BehaviourTreeNode implements IBehaviourTreeNode {

    private ICharacter character;
    private IMap map;
    private Consumer<IBehaviourTreeNode> action;
    private ArrayList<ChildrenCondition> ChildrenConditions;

    public BehaviourTreeNode() {
        action = new NullAction();
        ChildrenConditions = new ArrayList<>();
        map = null;
        character = null;
    }

    @Override
    public void setMap(IMap map) {
        this.map = map;
    }

    @Override
    public void setCharacter(ICharacter character) {
        this.character = character;
    }

    @Override
    public void setActionWhenRun(Consumer<IBehaviourTreeNode> action) {
        this.action = action;
    }

    @Override
    public void addChildrenNodeInOrder(Predicate<IBehaviourTreeNode> conditionToRunChildren, float probabilityToRunChildren, IBehaviourTree nodeToRun) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BehaviourTreeNodeStatus run(Object... args) {
        checkCorrectInitialization();
        action.accept(this);
        return BehaviourTreeNodeStatus.SUCCESS;
    }

    private void checkCorrectInitialization() throws RuntimeException {
        checkActionInitialized();
        checkCharacterInitialized();
        checkMapInitialized();
    }

    private void checkMapInitialized() throws RuntimeException {
        if (map == null) {
            throw new RuntimeException("node map is null");
        }
    }

    private void checkCharacterInitialized() throws RuntimeException {
        if (character == null) {
            throw new RuntimeException("node parent is null");
        }
    }

    private void checkActionInitialized() throws RuntimeException {
        if (action == null) {
            throw new RuntimeException("node action is null");
        }
    }

    private class ChildrenCondition {

        Predicate<IBehaviourTreeNode> conditionToRunChildren = null;
        float probabilityToRunChildren = 0f;
        IBehaviourTree nodeToRun = null;
    }

    private class NullAction implements Consumer<IBehaviourTreeNode> {
        @Override
        public void accept(IBehaviourTreeNode t) {
        }
    };
}
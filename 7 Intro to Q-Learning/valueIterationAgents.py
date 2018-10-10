# valueIterationAgents.py
# -----------------------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).

import math
from random import choice, random

class ValueIterationAgent:
    """
        A ValueIterationAgent takes a Markov Decision Process and runs value
        iteration using the supplied discount factor. It runs either a given number
        of iterations or until convergence to the convergencePrecision. It uses
        either straight value iteration (assumes that all actions are equally likely,
        the default choice) or q-value iteration (assumes the action taken is the one
        that would produce the best result given the current computed values).
    """

    def __init__(self, mdp, discount=0.9, maxIterations=200, defaultStateValue=0, valueType='value'):
        self.valueType = valueType.lower()
        if self.valueType not in {'value', 'qvalue'}:
            raise Exception("Wrong value type: {}. Must be either 'value' (default) or 'qvalue'" \
            				.format(valueType))

        self.mdp = mdp
        self.discount = discount
        self.maxIterations = maxIterations

        # Normally this would be 0. But play with it to see that the
        # final values are independent of the initial default value.
        # A larger-magnitude number takes longer to converge.
        # The early iterations mainly work off the discount
        # until the numbers are small enough to be affected by
        # the actual rewards.
        self.defaultStateValue = defaultStateValue

        # An empty dictionary.
        # The CS188 code uses a Counter, which is a subclass of Dictionary.
        # Must ensure that the value of the TERMINAL state is always 0, not the default value.
        self.values = {self.mdp.TERMINAL: 0}
        self.runValueIteration()


        ############################################################################################


    def doOneIteration(self):
        """
        One value or Q-value iteration.
        :return: a dictionary of new values or new Q-values derived from current values
        """
        return {state: self.getQValueOrValue(state) for state in self.mdp.getStates()}


    def getProbValues(self, state):
        """
        :param state: a state
        :return: a list of (prob, value) pairs for each possible action in this state
        """
        return [ (prob, self.getExpectedValue(state, action))
                               for (action, prob) in self.mdp.getSelectedActionProbs() ]


    def getDiscountedStateActionValue(self, state, action):
        """
        The Bellman equation
        :param state: a state
        :param action: an action
        :return: the discounted value of taking action when in state. (No noise.)
        """
        (reward, nextState) = self.mdp.getRewardAndNextState(state, action)
        return reward + self.discount * self.values.get(nextState, self.defaultStateValue)


    def getExpectedValue(self, state, action):
        """
        The Bellman equation with noise
        :param state: a state
        :param action: an action
        :return: the expected value if action is selecting in state. Includes the possibility of noise.
        """
        return sum([effActProb*self.getDiscountedStateActionValue(state, effAction)
                          for (effAction, effActProb) in self.mdp.getEffectiveActionProbs(action)])


    def getQValueOrValue(self, state):
        """
        Get either the q-value of the value for a state depending on self.valueType
        :param state: a state
        :return: the q-value of the value for the state.
        """
        probValues = self.getProbValues(state)
        # if actionProbValues is empty
        if not probValues:
            return 0
        else:
            vType = self.valueType
            values = [(value if vType == 'qvalue' else prob*value) \
                           for (prob, value) in probValues]
            return {'qvalue': max, 'value': sum}[vType](values)


    def printValues(self, i, someValues):
        """
        Prints the somveValues dictionary in array form.
        :param i: An iteration number
        :param someValues: a dictionary of state-values
        :return: None
        """
        print('\nIteration', i)
        # Use self.defaultStateValue if the only value is TERMINAL
        values = [abs(v) for v in someValues.values()] if len(someValues) > 1 \
                                                       else [abs(self.defaultStateValue)]
        mx = max(values)
        for y in reversed(range(self.mdp.MAX_Y+1)):
            for x in range(self.mdp.MAX_X+1):
                formatString = self.mdp.getFormatString(mx, (x, y))
                print(formatString.format(someValues.get((x, y), self.defaultStateValue)), end=' ')
            print()


    @staticmethod
    def roundDict(dict, p):
        """
        :param dict: a dictionary
        :param p: desired decimal precision
        :return: a copy of dict with values rounded to p decimal places
        """
        return {k: round(v, p) for (k, v) in dict.items()}


    def runValueIteration(self):
        """
        Runs value iteration
        :return: None
        """
        print('\n\n{}-iteration on {}'.format(self.valueType, self.mdp.NAME))
        (i, converged) = (0, False)
        self.printValues(i, self.values)
        while not converged and i < self.maxIterations:
            i += 1
            newValues = self.doOneIteration()

            # Check for convergence.
            converged = self.roundDict(self.values, self.mdp.CONV_PREC) == \
                        self.roundDict(newValues, self.mdp.CONV_PREC)

            # Print newValues only if they differ from the old values in the PRINT_PREC decimal place.
            if not converged and \
                self.roundDict(self.values, self.mdp.PRINT_PREC) != \
                self.roundDict(newValues, self.mdp.PRINT_PREC):
                self.printValues(i, newValues)

            # Update values.
            # Even if we have converged, the newValues will generally differ from the
            # current values beyond the convergencePrecision. So keep the new ones.
            self.values = newValues

        print('\n{} to {} decimal places after {} iterations with {} as the start value.'.
              format('Converged' if converged else 'Did not converge',
                     self.mdp.CONV_PREC,
                     i,
                     self.defaultStateValue))


class GridMDP:
    """
    Everything is static, i.e., defined at the class level, except methods that may use subclass information.
    """

    NORTH = 'north'
    EAST  = 'east'
    SOUTH = 'south'
    WEST  = 'west'

    DIRECTIONS = {NORTH: ( 0,  1),
                  EAST:  ( 1,  0),
                  SOUTH: ( 0, -1),
                  WEST:  (-1,  0)}

    # These are the actions (and their probabilities) that
    # are actually taken when a given action is selected
    EFFECTIVE_ACTION_PROBS = {NORTH: [(NORTH, 0.8), (EAST,  0.1), (WEST,  0.1)],
                              EAST:  [(EAST,  0.8), (SOUTH, 0.1), (NORTH, 0.1)],
                              SOUTH: [(SOUTH, 0.8), (EAST,  0.1), (WEST,  0.1)],
                              WEST:  [(WEST,  0.8), (SOUTH, 0.1), (NORTH, 0.1)]}

    # These are the probabilities of choosing any action at random.
    SELECTED_ACTION_PROBS = [(NORTH, 0.25),
                             (EAST,  0.25),
                             (SOUTH, 0.25),
                             (WEST,  0.25)]


    HIT_WALL_REWARD = 0
    DEFAULT_REWARD = 0

    # default state
    START_STATE = (0, 0)

    currentState = START_STATE
    
    stepCount = 0

    def isDone(self):
        """
        A generic test for completion of an episode: have we arrived at the terminal state?
        :return: Whether the episode is over?
        """
        return self.currentState == self.TERMINAL  

    # This is the name of the terminal state. If the agent ever enters it, it will always
    # transition to itself with no reward.
    # See first line of return of getRewardAndNextState.
    TERMINAL = 'terminal'

    INACCESSIBLE_STATES = { }

    PRINT_PREC = 2
    CONV_PREC = PRINT_PREC + 2

    def reset(self):
        """
        Initialize the environment
        :return:
        """
        self.currentState = self.START_STATE
        self.stepCount = 0
        return self.currentState

    def step(self, action):
        """
        Perform a step using action as the action.
        """
        floor = 1
        choice = random()
        effAct = None
        # This loop selects one of the effective actions in proportion to its probability
        for (effAct, prob) in self.getEffectiveActionProbs(action):
            if choice > floor - prob:
                break
            floor -= prob
        (reward, self.currentState) = self.getRewardAndNextState(self.currentState, effAct)
        self.stepCount += 1
        # Return the stepCount and the effective action in the "debug" field.
        return (self.currentState, reward, self.isDone(), (self.stepCount, effAct))

    @staticmethod
    def computeNextState(state, action):
        delta = GridMDP.DIRECTIONS[action]
        return (state[0] + delta[0], state[1] + delta[1])


    def getEffectiveActionProbs(self, action):
        return self.EFFECTIVE_ACTION_PROBS[action]


    def getFormatString(self, mx, state):
        ln = int(math.ceil(math.log10(max(mx, 1))))
        commas = max(0, (ln-1) // 3)
        fieldWidth = 5 + ln + commas
        formatString = ' ' + ('*' * (fieldWidth-1)) if state in self.getInaccessibleStates() else \
                             '{: ' + str(fieldWidth) + ',.' + str(self.PRINT_PREC) + 'f}'
        return formatString


    def getInaccessibleStates(self):
        return self.INACCESSIBLE_STATES


    def getPossibleActions(self):
        return [action for (action, _) in self.getSelectedActionProbs()]


    def getRewardAndNextState(self, state, action):
        return (0, self.TERMINAL) if state == self.TERMINAL else \
               (self.HIT_WALL_REWARD, state) if self.hitsAWall(state, action) else \
               (self.DEFAULT_REWARD, self.computeNextState(state, action))


    def getSelectedActionProbs(self):
        return self.SELECTED_ACTION_PROBS


    def getStates(self):
        return [self.TERMINAL] + \
               [(x, y) for x in range(self.MAX_X + 1) for y in range(self.MAX_Y + 1) \
                       if not (x, y) in self.getInaccessibleStates()]


    # Returns True for actions that would hit the Grid border.
    # Special cases are defined for each grid.
    def hitsAWall(self, state, action):
        (x, y) = state
        return (x, action) in {(0, self.WEST),  (self.MAX_X, self.EAST)} or \
               (y, action) in {(0, self.SOUTH), (self.MAX_Y, self.NORTH)}


class Sutton_Ex3_5(GridMDP):

    NAME = 'Sutton_Ex3_5'

    MAX_X = 4
    MAX_Y = 4

    HIT_WALL_REWARD = -1

    PRINT_PREC = 1
    CONV_PREC = PRINT_PREC + 2


    def getRewardAndNextState(self, state, action):
        return (10, (1, 0)) if state == (1, 4) else \
               ( 5, (3, 2)) if state == (3, 4) else \
               super().getRewardAndNextState(state, action)


class BookGrid(GridMDP):

    NAME = 'BookGrid'

    # Complete this.
    MAX_X = None
    MAX_Y = None

    # Inaccessible states are those that can't be reached.
    # The Sutton 3.5 grid doesn't have any.
    # The BookGrid has one.

    # Complete this. Should be a list of inaccessible states.
    # None is wrong even if all states are accessible.
    INACCESSIBLE_STATES = None


    # The terminal state is the state the agent goes to when it exits the two "exit" states,
    # i.e., the ones with values. These are the states in the upper right corner and the
    # one directly below it. The reward is accrued when the agent exits an exit state, not
    # when it enters it.
    def getRewardAndNextState(self, state, action):
        # Write this as a single expression. No need for additional code.
        return # The two ways to get to the TERMINAL state from
               # the two exit states. (Ends with "else \")
               # A final line to call the super class method for all other results.


    # Returns True if the agent's action bumps a wall, either at the grid border on internally.
    # Calls the super class for the grid borders. Use this method for the cell in the middle.
    def hitsAWall(self, state, action):
        # Your code. Make it a single expression. No procedural code.
        return # An attempt to move to (1, 1) or an attempt to leave the grid.
               # super().hitsAWall takes care of the second case.

class FourChain(GridMDP):

    NAME = 'FourChain'

    MAX_X = 4
    MAX_Y = 0
    

    # These are the actions (and their probabilities) that
    # are actually taken when a given action is selected.
    EFFECTIVE_ACTION_PROBS = {GridMDP.EAST: [(GridMDP.EAST, 0.8), (GridMDP.WEST, 0.2)],
                              GridMDP.WEST: [(GridMDP.WEST, 0.8), (GridMDP.EAST, 0.2)]}

    # These are the probabilities of choosing any action at random.
    SELECTED_ACTION_PROBS = [(GridMDP.EAST, 0.5),
                             (GridMDP.WEST, 0.5)]

    # The terminal state is the state the agent goes to when it exits the two states with values,
    # i.e., the ones in the upper right corner and the one directly below it.
    # The reward is accrued when the agent exits a value state, not when it enters them.
    
    # reward and next state based on current state and action.
    def getRewardAndNextState(self, state, action):
        if (action == "west"):
            return (2, (0,0))
        elif action == "east" and state == (4,0):
            return (10, (4,0))
        return super().getRewardAndNextState(state, action)
       

    # Returns True if the agent's action bumps a wall, either at the grid border on internally.
    # Calls the super class for the grid borders. Use this method for the cell in the middle.
    def hitsAWall(self, state, action):
        return False


    #### #### #### #### #### #### Env methods #### #### #### #### #### ####

    STEP_LIMIT = 1000

    # This is the only method you have to override.
    def isDone(self):
        return self.stepCount == self.STEP_LIMIT


if __name__ == '__main__':
    # ValueIterationAgent(Sutton_Ex3_5(), valueType='value', defaultStateValue=-100) # The values in Sutton and Barto.
    # Complete the definition of the BookGrid before running this line.
    # ValueIterationAgent(BookGrid(), valueType='qvalue')    # What the CS 188 code computes.
    # These two compute the other value iteration types. Uncomment them to see the differences.
    # ValueIterationAgent(Sutton_Ex3_5(), valueType='qvalue')
    # ValueIterationAgent(FourChain(), valueType="value")
    # ValueIterationAgent(BookGrid(), valueType='value')

    env = FourChain()
    state = env.reset()
    done = False
    while not done:
        # Select a random action.
        action = env.getPossibleActions()[choice(range(2))]
        # Call step with that action and get a response.
        # Note: effAct is the action that was performed, which,
        # because of noise, may not be the same as action.
        (nextState, reward, done, (stepCount, effAct)) = env.step(action)
        # Print out the state, action, and response.
        print('{}. state: {}, action: {}, effAct: {}, reward: {}, nextState: {}, done: {}' \
              .format(stepCount, state, action, effAct, reward, nextState, done))
        # Update state to nextState.
        state = nextState
    
    ValueIterationAgent(FourChain(), valueType="qvalue")
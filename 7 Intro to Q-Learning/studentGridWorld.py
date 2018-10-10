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

from math import ceil, log10
from random import choice, random

class ValueIterationAgent:
    """
        A ValueIterationAgent takes a Markov Decision Process and runs value
        iteration using the supplied discount factor. It runs either a given number
        of iterations or until convergence to the convergencePrecision. It uses
        either straight value iteration (assumes that all actions are equally likely,
        the default choice) or q-value iteration (assumes the action taken is the one
        that would produce the best result given the current computed values).


    >>> bg = ValueIterationAgent(BookGrid(), valueType='qvalue')
    >>> bg.printValues(0, bg.values)
Iteration 15
 0.64-e  0.74-e  0.85-e  1.00-*
 0.57-n  ****    0.57-n -1.00-*
 0.49-n  0.43-w  0.48-n  0.28-w
    """

    def __init__(self, mdp, discount=0.9, maxIterations=200, defaultStateValue=0, valueType='value'):
        self.valueType = valueType.lower()
        if self.valueType not in {'value', 'qvalue'}:
            raise Exception(f"Wrong value type: {valueType}. Must be either 'value' (default) or 'qvalue'")

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
        self.values = {self.mdp.TERMINAL: ('-',0)} if self.mdp.HAS_TERMINAL_STATE else {}
        self.runValueIteration()


        ############################################################################################


    def doOneIteration(self):
        """
        One value or Q-value iteration.
        :return: a dictionary of new values or new Q-values derived from current values
        """
        return {state: self.getQValueOrValue(state) for state in self.mdp.getStates()}


    def getActionProbValues(self, state):
        """
        :param state: a state
        :return: a list of (prob, value) pairs for each possible action in this state
        """
        return [ (action, prob, self.getExpectedValue(state, action))
                               for (action, prob) in self.mdp.getSelectedActionProbs() ]


    def getDiscountedStateActionValue(self, state, action):
        """
        The Bellman equation
        :param state: a state
        :param action: an action
        :return: the discounted value of taking action when in state. (No noise.)
        """
        (reward, nextState) = self.mdp.getRewardNextState(state, action)
        return reward + self.discount * self.values.get(nextState, ('-', self.defaultStateValue))[1]


    def getExpectedValue(self, state, action):
        """
        The Bellman equation with noise
        :param state: a state
        :param action: the selected action
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

        def getQValue(values):
            mx = max(values, key=(lambda t: t[1]))[1]
            actions = [act[0] for (act, val) in values if val == mx]
            s = '  ' if len(actions) == 0 else \
                actions[0]+' ' if len(actions) == 1 else \
                actions[0]+actions[1] if len(actions) == 2 else \
                '?' + str(len(actions))
            return (s, mx)

        def getValue(values):
            return ('-', sum([v for (_, v) in values]))

        actProbValues = self.getActionProbValues(state)
        # if actionProbValues is empty
        if not actProbValues:
            return 0
        else:
            vType = self.valueType
            values = [((action, value) if vType == 'qvalue' else ('-', prob*value)) \
                           for (action, prob, value) in actProbValues]
            return {'qvalue': getQValue, 'value': getValue}[vType](values)


    def printValues(self, i, someValues):
        """
        Prints the somveValues dictionary in array form.
        :param i: An iteration number
        :param someValues: a dictionary of state-values
        :return: None
        """
        print('\nIteration', i)
        # Use self.defaultStateValue if the only value is TERMINAL
        values = [abs(v) for (_, v) in someValues.values()] if len(someValues) > 1 \
                                                       else [abs(self.defaultStateValue)]
        mx = max(values)
        for y in reversed(range(self.mdp.MAX_Y+1)):
            for x in range(self.mdp.MAX_X+1):
                formatString = self.mdp.getFormatString(mx, (x, y), {'qvalue':'** ', 'value':''}[self.valueType])
                (act, val) = someValues.get((x, y), ('-', self.defaultStateValue))
                print(formatString.format(val, '' if act[0]=='-' else '-'+act), end=' ')
            print()


    @staticmethod
    def roundDict(dict, p):
        """
        :param dict: a dictionary
        :param p: desired decimal precision
        :return: a copy of dict with values rounded to p decimal places
        """
        return {k: (a, round(v, p)) for (k, (a, v)) in dict.items()}


    def runValueIteration(self):
        """
        Runs value iteration
        :return: None
        """
        print(f'\n\n{self.valueType}-iteration on {self.mdp.NAME}')
        (i, converged) = (0, False)
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
            # current values beyond the convergencePrecision. So save the new ones.
            self.values = newValues

        print(f'\n{"Converged" if converged else "Did not converge"} ' +
              f'to {self.mdp.CONV_PREC} decimal places after {i} iterations ' +
              f'with {self.defaultStateValue} as the start value.')



class Env:
    """
    This is an Env class. GridMDP inherits from it.
    It include constants, variables and methods that let a Grid act a an environment.
    """

    # The default start state. Declare a different one is a Grid if necessary.
    START_STATE = (0, 0)

    # A variable used by the environment to keep track of the current state.
    currentState = START_STATE

    # Counts the steps (as a logging aid). Other grids can use this for their own puposes.
    stepCount = 0


    def isDone(self):
        """
        A generic test for completion of an episode: have we arrived at the terminal state?
        :return: Whether the episode is over?
        """
        return self.currentState == self.TERMINAL


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
        (reward, self.currentState) = self.getRewardNextState(self.currentState, effAct)
        self.stepCount += 1
        # Return the stepCount and the effective action in the "debug" field.
        return (self.currentState, reward, self.isDone(), (self.stepCount, effAct))


class GridMDP(Env):
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

    # This is the name of the terminal state. If the agent ever enters it, it will always
    # transition to itself with no reward.
    # See first line of return of getRewardNextState.
    TERMINAL = 'terminal'

    HAS_TERMINAL_STATE = True

    INACCESSIBLE_STATES = set( )

    START_STATE = (0, 0)
    currentState = START_STATE

    PRINT_PREC = 2
    CONV_PREC = PRINT_PREC + 2

    @staticmethod
    def computeNextState(state, action):
        delta = GridMDP.DIRECTIONS[action]
        return (state[0] + delta[0], state[1] + delta[1])


    def getEffectiveActionProbs(self, action):
        return self.EFFECTIVE_ACTION_PROBS[action]


    def getFormatString(self, mx, state, extra):
        ln = int(ceil(log10(max(mx, 1))))
        commas = max(0, (ln-1) // 3)
        fieldWidth = 5 + ln + commas
        formatString = ' ' + ('*' * (fieldWidth-1)+extra) if state in self.getInaccessibleStates() else \
                             '{: ' + str(fieldWidth) + ',.' + str(self.PRINT_PREC) + 'f}{}'
        return formatString


    def getInaccessibleStates(self):
        return self.INACCESSIBLE_STATES


    def getPossibleActions(self):
        return [action for (action, _) in self.getSelectedActionProbs()]


    def getRewardNextState(self, state, action):
        return (0, self.TERMINAL) if state == self.TERMINAL else \
               (self.HIT_WALL_REWARD, state) if self.hitsAWall(state, action) else \
               (self.DEFAULT_REWARD, self.computeNextState(state, action))


    def getSelectedActionProbs(self):
        return self.SELECTED_ACTION_PROBS


    def getStates(self):
        return ([self.TERMINAL] if self.HAS_TERMINAL_STATE else []) + \
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

    HAS_TERMINAL_STATE = False

    HIT_WALL_REWARD = -1

    PRINT_PREC = 1
    CONV_PREC = PRINT_PREC + 2


    def getRewardNextState(self, state, action):
        return (10, (1, 0)) if state == (1, 4) else \
               ( 5, (3, 2)) if state == (3, 4) else \
               super().getRewardNextState(state, action)


class BookGrid(GridMDP):

    NAME = 'BookGrid'

    MAX_X = 3
    MAX_Y = 2

    # Inaccessible states are those that can't be reached.
    # The Sutton 3.5 grid doesn't have any.
    # The BookGrid has one.
    INACCESSIBLE_STATES = {(1, 1)}


    def getInaccessibleStates(self):
        return self.INACCESSIBLE_STATES.union(super().getInaccessibleStates())


    # The terminal state is the state the agent goes to when it exits the two states with values,
    # i.e., the ones in the upper right corner and the one directly below it.
    # The reward is accrued when the agent exits a value state, not when it enters them.
    def getRewardNextState(self, state, action):
        return ( 1, GridMDP.TERMINAL) if state == (3, 2) else \
               (-1, GridMDP.TERMINAL) if state == (3, 1) else \
               super().getRewardNextState(state, action)


    # Returns True if the agent's action bumps a wall, either at the grid border on internally.
    # Calls the super class for the grid borders. Use this method for the cell in the middle.
    def hitsAWall(self, state, action): #, maxX=MAX_X, maxY=MAX_Y):
        return super().computeNextState(state, action) == (1, 1) or \
               super().hitsAWall(state, action) #, maxX, maxY)


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
    def getRewardNextState(self, state, action):
        if (action == "west"):
            return (2, (0,0))
        elif action == "east" and state == (4,0):
            return (10, (4,0))
        return super().getRewardNextState(state, action)
       

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

    # The following code tests BookGrid() or FourChain() as an environment.
    # Uncomment the one you want to test.

    # env = BookGrid()
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


    # The lines below run various value-iteration examples.
    # Note that when qvalue is run, the output indicates the best action.
    # If there are two equally good actions, both are listed. If more: ? and the number of choices.
    # ValueIterationAgent(BookGrid(), valueType='qvalue')    # What the CS 188 code computes.
    """
    0.64-e   0.74-e   0.85-e   1.00-?4 
    0.57-n   ******   0.57-n  -1.00-?4 
    0.49-n   0.43-w   0.48-n   0.28-w  
    """

    # ValueIterationAgent(Sutton_Ex3_5(), valueType='value', defaultStateValue=-100) # The values in Sutton and Barto.
    """
     3.3    8.8    4.4    5.3    1.5 
     1.5    3.0    2.2    1.9    0.5 
     0.1    0.7    0.7    0.4   -0.4 
    -1.0   -0.4   -0.4   -0.6   -1.2 
    -1.9   -1.3   -1.2   -1.4   -2.0  
    """

    ValueIterationAgent(FourChain(), discount=0.95, maxIterations=250, valueType='qvalue')
    """
    61.38-e   64.89-e   69.51-e   75.59-e   83.59-e
    """

    # These compute the other value iteration possibilities. Uncomment them to see the differences.
    # ValueIterationAgent(BookGrid(), valueType='value')
    """
     0.04  0.11  0.24  1.00 
    -0.01  **** -0.30 -1.00 
    -0.06 -0.14 -0.28 -0.52 
    """

    # ValueIterationAgent(Sutton_Ex3_5(), valueType='qvalue')
    """
    17.1-e     19.8-?4    17.1-w     16.0-?4    13.8-w  
    15.1-n     17.0-n     15.1-n     14.0-n     12.3-w  
    13.3-n     14.6-n     13.3-n     12.3-n     10.8-n  
    11.6-n     12.6-n     11.7-n     10.7-n      9.5-n  
    10.2-n     10.9-n     10.2-n      9.4-n      8.4-n  
    """

    # ValueIterationAgent(FourChain(), discount=0.95, maxIterations=250, valueType='value')
    """
    25.09   25.63   26.75   29.13   34.13
    """

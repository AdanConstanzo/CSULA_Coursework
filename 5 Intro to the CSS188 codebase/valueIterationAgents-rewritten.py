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

class ValueIterationAgent:
    """
        A ValueIterationAgent takes a Markov Decision Process and runs value
        iteration using the supplied discount factor. It runs either a given number
        of iterations or until convergence to the convergencePrecision. It uses
        either straight value iteration (assumes that all actions are equally likely,
        the default choice) or q-value iteration (assumes the action taken is the one
        that would produce the best result given the current computed values).
    """

    def __init__(self, mdp, discount=0.9, maxIterations=200, defaultStateValue=-100, valueType='value'):
        self.valueType = valueType.lower()
        if self.valueType not in {'value', 'qvalue'}:
            raise Exception("Wrong value type: {}. Must be either 'value' (default) or 'qvalue'".format(valueType))

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


    ################ use Values (computed without action choices) ##############################

    def doOneValueIteration(self):
        """
        :return: a dictionary of new values derived from current values based on value iteration
        """

        return {state: self.getStateValue(state) for state in self.mdp.getStates()}

    def getStateValue(self, state):
        """
        :return: the approximated value of state given current values using the Bellman equation.
        """
        return sum([prob * self.getDiscountedStateActionValue(state, action)
                    for (action, prob) in self.mdp.getActionProbPairs(state)])


    ################ use QValues (computed using action choices) ##############################

    def doOneQValueIteration(self):
        """
        :return: a dictionary of new values derived from current values based on qvalue iteration
        """
        return {state: self.getStateQValue(state) for state in self.mdp.getStates()}

    def getStateActionQValue(self, state, action):
        """
          Compute the Q-value of action in state from the
          value function stored in self.values.
        """
        effectiveActionProbs = self.mdp.getEffectiveActionProbs(action)
        return sum([prob*self.getDiscountedStateActionValue(state, effAction) \
                    for (effAction, prob) in effectiveActionProbs])

    def getStateQValue(self, state):
        """
        :return: the approximated q-value of state given current values using the Bellman equation.
        """
        qValues = [self.getStateActionQValue(state, action) for action in self.mdp.getPossibleActions(state)]
        return max(qValues) if qValues else 0

    ###########################################################################################

    def getDiscountedStateActionValue(self, state, action):
        """
        :return: the discounted value of taking action when in state.
        """
        (reward, nextState) = self.mdp.getRewardAndNextState(state, action)
        return reward + self.discount*self.values.get(nextState, self.defaultStateValue)


    def printValues(self, i, someValues):
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
        print('\n\n{}-iteration on {}'.format(self.valueType, self.mdp.NAME))
        i = 0
        self.printValues(i, self.values)
        convergedString = 'Did not converge'
        for i in range(1, self.maxIterations):
            newValues = self.doOneValueIteration()  if self.valueType == 'value'  else \
                        self.doOneQValueIteration() # if valueType == 'QValue'

            # Print the newValues only if they differ from old values in the PRINT_PREC decimal place.
            if self.roundDict(self.values, self.mdp.PRINT_PREC) != self.roundDict(newValues, self.mdp.PRINT_PREC):
                self.printValues(i, newValues)

            # Check for convergence.
            converged = self.roundDict(self.values, self.mdp.CONV_PREC) == \
                        self.roundDict(newValues, self.mdp.CONV_PREC)

            # Update values.
            # Even if we have converged, the newValues will generally differ from the
            # current values beyond the convergencePrecision. So keep the new ones.
            self.values = newValues

            # Stop iterating if we have converged.
            if converged:
                convergedString = 'Converged'
                break

        print('\n' + convergedString + ' to {} decimal places after {} iterations.'.
              format(self.mdp.CONV_PREC, i+1))


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

    # These are the probabilities of choosing any action at random.
    ACTION_PROB_PAIRS = [(NORTH, 0.25),
                         (EAST,  0.25),
                         (SOUTH, 0.25),
                         (WEST,  0.25)]

    # These are the actions (and their probabilities) that
    # are actually taken when a given action is selected
    EFFECTIVE_ACTION_PROBS = {NORTH: [(NORTH, 0.8), (EAST,  0.1), (WEST,  0.1)],
                              EAST:  [(EAST,  0.8), (SOUTH, 0.1), (NORTH, 0.1)],
                              SOUTH: [(SOUTH, 0.8), (EAST,  0.1), (WEST,  0.1)],
                              WEST:  [(WEST,  0.8), (SOUTH, 0.1), (NORTH, 0.1)]}


    HIT_WALL_REWARD = 0
    DEFAULT_REWARD = 0

    # This is the name of the terminal state. If the agent ever enters it, it will always
    # transition to itself with no reward.
    # See first line of return of getRewardAndNextState.
    TERMINAL = 'terminal'

    INACCESSIBLE_STATES = { }

    PRINT_PREC = 2

    CONV_PREC = PRINT_PREC + 2

    @staticmethod
    def computeNextState(state, action):
        delta = GridMDP.DIRECTIONS[action]
        return (state[0] + delta[0], state[1] + delta[1])


    def getActionProbPairs(self, state):
        return self.ACTION_PROB_PAIRS


    def getEffectiveActionProbs(self, action):
        return self.EFFECTIVE_ACTION_PROBS[action]


    def getFormatString(self, mx, state):
        ln = int(round(math.log10(max(mx, 1))))
        commas = max(0, (ln-1) // 3)
        fieldWidth = 5 + ln + commas
        formatString = ' ' + ('X' * (fieldWidth-1)) if state in self.INACCESSIBLE_STATES else \
                       '{: ' + str(fieldWidth) + ',.' + str(self.PRINT_PREC) + 'f}'
        return formatString


    def getInaccessibleStates(self):
        return self.INACCESSIBLE_STATES


    def getPossibleActions(self, state):
        return [action for (action, _) in self.getActionProbPairs(state)]


    def getRewardAndNextState(self, state, action): #, maxX, maxY, hitWallReward, defaultReward):
        return (0, self.TERMINAL) if state == self.TERMINAL else \
               (self.HIT_WALL_REWARD, state) if self.hitsAWall(state, action) else \
               (self.DEFAULT_REWARD, self.computeNextState(state, action))


    def getStates(self):
        return [(x, y) for x in range(self.MAX_X + 1) for y in range(self.MAX_Y + 1) \
                       if not (x, y) in self.getInaccessibleStates()] + [self.TERMINAL]


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
        # Your code. Make it a single expression. No need for procedural code.
        return # An attempt to move to (1, 1) or an attempt to leave the grid.
               # super().hitsAWall takes care of the second case.

if __name__ == '__main__':
    ValueIterationAgent(Sutton_Ex3_5(), valueType='value') # The values in Sutton and Barto.
    ValueIterationAgent(BookGrid(), valueType='qvalue')    # What the CS 188 code computes.

    # These two compute the other value iteration types. Uncomment them to see the differences.
    # ValueIterationAgent(Sutton_Ex3_5(), valueType='qvalue')
    # ValueIterationAgent(BookGrid(), valueType='value')

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
        iteration using the supplied discount factor. It run either a given number
        of iterations or until we converge to the convergencePrecision.
    """

    def __init__(self, mdp, discount=0.9, maxIterations=200, convergencePrecision=3):
        self.mdp = mdp
        self.discount = discount
        self.maxIterations = maxIterations
        self.convergencePrecision = convergencePrecision

        # Normally this would be 0. But play with it to see that the
        # final values are independent of the initial default value.
        # A larger-magnitude number takes longer to converge.
        # The early iterations mainly work off the discount
        # until the numbers are small enough to be affected by
        # the actual rewards.
        self.defaultStateValue = -1000000

        # An empty dictionary.
        # The CS188 code uses a Counter, which is a subclass of Dictionary.
        self.values = {}
        self.runValueIteration()


    def doOneIteration(self):
        """
        :return: a dictionary of new values derived from current values
        """
        return {state: self.getStateValue(state) for state in self.mdp.getStates()}


    def getStateValue(self, state):
        """
        :return: the approximated value of state given current values using the Bellman equation.
        """
        return sum([prob * self.getDiscountedStateActionValue(state, action)
                    for (action, prob) in self.mdp.getActionProbPairs(state)])


    def getDiscountedStateActionValue(self, state, action):
        """
        :return: the discounted value of taking action when in state.
        """
        (reward, nextState) = self.mdp.getRewardNextStatePair(state, action)
        return reward + self.discount*self.values.get(nextState, self.defaultStateValue)


    @staticmethod
    def printValues(i, someValues):
        print('\nIteration', i)
        mx = max(map(abs, someValues.values()))
        ln = round(math.log10(mx))
        commas = round(ln/3)
        fieldWidth = str(4 + ln + commas)
        formatString = '{: ' + fieldWidth + ',.1f}'
        for y in reversed(range(5)):
            for x in range(5):
                print(formatString.format(someValues[(x, y)]), end=' ')
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
        convergedString = 'Did not converge'
        for i in range(self.maxIterations):
            newValues = self.doOneIteration()

            # Print the newValues only if they differ from old values in the first decimal place.
            if self.roundDict(self.values, 1) != self.roundDict(newValues, 1):
                self.printValues(i, newValues)

            # Check for convergence.
            converged = self.roundDict(self.values, self.convergencePrecision) == \
                        self.roundDict(newValues, self.convergencePrecision)

            # Update values.
            # Even if we converged, the newValues will generally from the current values
            # differ beyond convergencePrecision. So keep the new ones.
            self.values = newValues

            # Stop iterating if we have converged.
            if converged:
                convergedString = 'Converged'
                break

        print('\n' + convergedString + ' to {} decimal places in {} iterations.'.
              format(self.convergencePrecision, i+1))


class MyMDP:
    """
    Everything is static, i.e., defined at the class level.
    """

    NORTH = 'north'
    EAST = 'east'
    SOUTH = 'south'
    WEST = 'west'


    DIRECTIONS = {NORTH: ( 0,  1),
                  EAST:  ( 1,  0),
                  SOUTH: ( 0, -1),
                  WEST:  (-1,  0)}


    ACTION_PROB_PAIRS = [(NORTH, 0.25),
                         (EAST,  0.25),
                         (SOUTH, 0.25),
                         (WEST,  0.25)]


    STATES = [(x, y) for x in range(5) for y in range(5)]


    @staticmethod
    def computeNextState(state, action):
        delta = MyMDP.DIRECTIONS[action]
        return (state[0] + delta[0], state[1] + delta[1])


    @staticmethod
    def getActionProbPairs(state):
        return MyMDP.ACTION_PROB_PAIRS


    @staticmethod
    def getRewardNextStatePair(state, action):
        return (10, (1, 0)) if state == (1, 4) else \
               ( 5, (3, 2)) if state == (3, 4) else \
               (-1,  state) if MyMDP.hitsAWall(state, action) else \
               ( 0, MyMDP.computeNextState(state, action))


    @staticmethod
    def getStates():
        return MyMDP.STATES


    @staticmethod
    def hitsAWall(state, action):
        (x, y) = state
        return (x, action) in {(0, MyMDP.WEST),  (4, MyMDP.EAST)} or \
               (y, action) in {(0, MyMDP.SOUTH), (4, MyMDP.NORTH)}


if __name__ == '__main__':
    ValueIterationAgent(MyMDP())

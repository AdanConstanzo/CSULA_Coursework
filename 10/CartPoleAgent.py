
import gym
import numpy as np
import matplotlib.pyplot as plt
import random
import util
import functools

def plot_results(results_list):
    # Make the y-axis start at 0. (There is probably a better way.)
    plt.plot([0], 'r')
    plt.plot(results_list)
    plt.ylabel('Episodes until success')
    plt.xlabel('Trial')
    plt.show()


def print_plot_results(results, max_episodes, n_trials):
    print(f'\nmax episodes: {max_episodes}; avg episodes: {np.round(np.sum(results) / n_trials, 1)}')
    plot_results(results)

    # plt.hist(results, bins=50, color='g', density=1, alpha=0.75)
    # plt.xlabel('Episodes required to reach 200')
    # plt.ylabel('Frequency')
    # plt.title('Histogram of Custom-coded solution')
    # plt.show()

def roundDictValues(d, p=1):
    return {k: round(v, p) for (k, v) in d.items()}

def weightedAvg(low, weight, high):
    """
    :param low: The value to return if weight == 0
    :param weight: a number in 0 .. 1
    :param high: The value to return if weight == 1
    :return: low*(1-weight) + high*weight
    """
    return low * (1 - weight) + high * weight

####################################################################################
####################################################################################
####################################################################################

class CartPole_Q_Learning:

    def __init__(self, strategyID=3, isQBot=False, testTrials=5, envName='CartPole-v0', render=True):
        self.strategyID = strategyID
        self.converged = not isQBot
        self.env = gym.make(envName)
        self.gamma = 0.99
        self.isQBot = isQBot
        self.possibleActions = [0, 1]
        self.prevAction = None
        self.prevState = None
        self.prevQValues = None
        # the qValues are {state: (0, {}), state: (0, {}), ...), where
        # each state is associated with its current estimated value along with
        # a dictionary of possible actions.
        # The action dictionary consists of a dictionary of {action: {}}, where
        # each action is associated with a dictionary of the resulting states
        # along with a count of the number of times that state occurred.
        self.render = render
        self.stepsCycle = 1000
        self.testTrials = testTrials
        self.trainingStepLimit = 5000
        self.trainingSteps = 0
        self.trialType = None
        self.weights = util.Counter()

    # Custom Functions
    def getFeatures(self, pos, vel, ang, rot):
        features = util.Counter()
        features["position"] = pos
        features["velocity"] = vel
        features["angle"] = ang
        features["rotation"] = rot
        return features

    def getWeights(self):
        return self.weights

    def computeValueFromQValues(self, state):
        """
          Returns max_action Q(state,action)
          there are no legal actions, which is the case at the
          terminal state, you should return a value of 0.0.
        """
        "*** YOUR CODE HERE ***"
        values = [self.getQValue(state, action) for action in self.getLegalActions(state)]
        return max(values) if len(values) > 0 else 0.0

    def getStateAction(self, pos, vel, ang, rot):
        return (0, 0) if self.getQValue(pos, vel, ang, rot, 0) >= self.getQValue(pos, vel, ang, rot, 1) else (1,1)

    def getActionFromState(self, pos, vel, ang, rot):
        return 0 if self.getQValue(pos, vel, ang, rot, 0) >= self.getQValue(pos, vel, ang, rot, 1) else 1

    def getQValue(self, pos, vel, ang, rot, action):
        features = self.getFeatures(pos, vel, ang, rot)
        return functools.reduce(lambda a, b: a + b,
                                [features[feature] * self.getWeights()[feature] for feature in features])

    self.computeValueFromQValues(nextState)

    def update(self, state, action, nextState, reward):
        """
           Should update your weights based on transition
        """
        "*** YOUR CODE HERE ***"
        features = self.getFeatures(*state)
        for feature in features:
            difference = (reward + self.gamma * self.computeValueFromQValues(nextState)) - self.getQValue(*state, action)
            self.weights[feature] = self.weights[feature] + self.alphaFn() * difference * features[feature]

    # End Custom Fuctions

    def alphaFn(self):
        # Note that 1000 / (1000 + 1000) => 0.5
        # Note that 1000 / (2000 + 1000) => 0.33
        # Note that 1000 / (3000 + 1000) => 0.25
        n = 5000
        return n / (self.trainingSteps + n)

    def epsilonFn(self):
        # Note that 0.75 * 0.99 ** 100 => 0.27
        # Note that 0.75 * 0.99 ** 200 => 0.1
        # 0.75 * 0.99**self.trainingSteps
        return 0 if self.trialType == 'test' else 1.0



    def main(self):
        # If we are running a QBot, first do training.
        if self.isQBot:
            trial_nbr = 0
            self.trialType = "training"
            trialsNeeded = 100
            while self.trainingSteps < self.trainingStepLimit or \
                  trialsNeeded == 1:
                trial_nbr += 1
                trialsNeeded = self.runTrial(trial_nbr)

        self.trialType = "test"
        if self.isQBot:
            print('\n\nFinished training. Setting epsilon to 0.')

            self.qValues = {'s1-1': {0:0, 1:1},
                            's2-0': {0:1, 1:0},
                            's3-1': {0:0, 1:1},
                            's4-0': {0:1, 1:0}
                            }
            self.printQvalues()

        for trial_nbr in range(self.testTrials):
            self.runTrial(trial_nbr)

    def printQvalues(self):
        print(f'\n\nAfter {self.trainingSteps} training steps. ', end='')
        print(f'epsilon: {round(self.epsilonFn(), 2)}, alpha: {round(self.alphaFn(), 2)}')
        for state in sorted(self.qValues):
            print(state, end=' -> ')
            actionQValues = self.qValues[state]
            for action in reversed(sorted(actionQValues, key=lambda a: actionQValues[a])):
                print(f'{action}: {round(actionQValues[action], 3)}', end='; ')
            print()
        print()

    def runEpisode(self):
        """
        An episode starts with env.reset() and ends either after 200 steps or when pole goes out of bounds.
        :return: whether the episode succeeded
        """
        observation = self.env.reset()
        (currentState, action) = self.getStateAction(*observation)
        episodeStepCount = 0
        while True:
            # observation = a list of four numbers (not a tuple)
            #   [cart position, cart velocity, pole angle, pole rotation rate]
            # if self.isQBot:
            #     action = self.getActionFromState(currentState)

            # print(f'episodeStepCount: {episodeStepCount}. {action}')
            action = self.getActionFromState(*observation)
            (observation, reward, done, debug) = self.env.step(action)

            (nextState, nextAction) = self.getStateAction(*observation)

            if self.isQBot and not self.converged:
                self.update(observation, nextAction, nextState,reward)

            if self.render:
                self.env.render()

            episodeStepCount += 1
            self.trainingSteps += 1

            # env returns done when either the pole goes out of bounds or 200 steps have been taken
            if done:
                return episodeStepCount == 200

            (currentState, action) = (observation, nextAction)

    def runTrial(self, trial_nbr):
        """
        A trial is a series of episodes until an episode succeeds, i.e., 200 steps without exceeding limits.
        :param trialType: "train" or test
        :param trial_nbr:
        :return: The number of episodes required to succeed.
        """
        episodeNbr = 0
        while self.trialType == 'test' or self.trainingSteps < self.trainingStepLimit:
            episodeNbr += 1
            if self.runEpisode():
                print(f'{self.trialType}: {trial_nbr + 1}; episodes: {episodeNbr}')
                return episodeNbr
        return episodeNbr



if __name__ == '__main__':
    CartPole_Q_Learning(strategyID=5, isQBot=True).main()
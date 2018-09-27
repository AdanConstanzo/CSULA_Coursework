
import gym
import numpy as np
import matplotlib.pyplot as plt


def main(n_trials=500):
    def episode_reward(env=gym.make('CartPole-v0')):
        observation = env.reset()
        total_reward = 0
        for _ in range(200):
            # observation = [position of cart, velocity of cart, angle of pole, rotation rate of pole]
            # limits are +/- 2.4 and +/- 0.75 radians (43 degrees)

            # Works
            action = \
                     1 if observation[1] < -0.9 else \
                     0 if observation[1] >  0.9 else \
                     1 if observation[3] >  0.06 else \
                     0 if observation[3] < -0.06 else \
                     1 if observation[2] >  0.005 else \
                     0 if observation[2] < -0.005 else \
                     1 if observation[0] <  0 else 0

            # Works
            # action = \
            #          1 if observation[1] < -0.9 else \
            #          0 if observation[1] >  0.9 else \
            #          1 if observation[3] >  0.06 else \
            #          0 if observation[3] < -0.06 else \
            #          1 if observation[2] >  0.0 else 0

            # Works
            # action = \
            #          1 if observation[1] < -0.9 else \
            #          0 if observation[1] >  0.9 else \
            #          1 if observation[2] >  0.01 else \
            #          0 if observation[2] < -0.01 else \
            #          1 if observation[3] >  0.01 else \
            #          0 if observation[3] < -0.01 else \
            #          1 if observation[0] <  0 else 0

            # Works
            # action = 1 if observation[1] < -0.9 else \
            #          0 if observation[1] > 0.9 else \
            #          1 if observation[2] > 0 else 0


            (observation, step_reward, done, info) = env.step(action)
            if n_trials <= 10:
                env.render()
            total_reward += step_reward
            if done:
                return total_reward

    def train(trial_nbr):
        # Do you understand why 10,000?
        for n in range(10000):
            if episode_reward() == 200:
                print('trial: {}; episodes: {}'.format(trial_nbr + 1, n + 1))
                return n + 1

    def print_plot_results(max_episodes):
        print('\nmax episodes: {}; avg episodes: {}'.format(max_episodes, np.round(np.sum(results) / n_trials, 1)))
        plt.hist(results, bins=50, color='g', density=1, alpha=0.75)
        plt.xlabel('Episodes required to reach 200')
        plt.ylabel('Frequency')
        plt.title('Histogram of Custom-codes solution')
        plt.show()

    # These two lines are the body of main()
    results = [train(trial_nbr) for trial_nbr in range(n_trials)]
    print_plot_results(max(results))


# Will render if the arg to main() is less than or equal to 10
main(5)

import gym
import random
import matplotlib.pyplot as plt


def main(n_trials=5):
  
  # observation = [position of cart, velocity of cart, angle of pole, rotation rate of pole]
  # 1 = right, 0 = left
  def getAction(observation):

    velocity_cart = observation[1]
    angle_pole = observation[2]

    # velocity
    # velocity cart affects movement aswell of angle of pole.
    if velocity_cart <= -0.5:
      return 1
    elif velocity_cart > 0.5:
      return 0
    # angle
    # if angle exceeds +/- 12 degrees
    elif angle_pole > 0:
      return 1
    else:
      return 0


  
  def episode_reward(parameters, env=gym.make('CartPole-v0')):
    observation = env.reset()
    total_reward = 0
    for _ in range(200):
      # limits are +/- 2.4 and +/- 0.75 radians (43 degrees)
      (observation, step_reward, done, info) = env.step(getAction(observation))
      if n_trials <= 5:
        env.render()
      total_reward += step_reward
      if done:
        return total_reward
    # returns when reaches 200 reward
  def train(trial_nbr):
    n = 1
    while True:
      parameters = [random.uniform(-1, 1) for _ in range(4)]
      if episode_reward(parameters) == 200:
        print('trial: {}; episodes: {}'.format(trial_nbr, n), end = "; ")
        print('parameters:', [round(p, 3) for p in parameters])
        return n
      n += 1
    # plot at end 
  def print_plot_results(max_episodes):
    print('\nmax episodes: {}; avg episodes: {}'. \
          format(max_episodes, round(sum(results)/n_trials, 1)))
    plt.hist(results, color='g') #, density=1, alpha=0.75, bins=50
    # plt.hist(results, bins=list(range(max(max_episodes+3, 5))), color='r', rwidth = 0.1, align = 'left') #, alpha=0.75, density=1)
    plt.xlabel('Episodes required to reach 200')
    plt.ylabel('Frequency')
    plt.title('Histogram of Random Search')
    plt.show()
    
  # These two lines are the body of main()
  # The functions above are nested within main()
  results = [train(trial_nbr + 1) for trial_nbr in range(n_trials)]
  print_plot_results(max(results))


# Will render if the arg to main() is less than or equal to 5
main()
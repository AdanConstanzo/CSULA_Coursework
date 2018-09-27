'''
  Code is from Open Gym
  http://gym.openai.com/docs/


'''

# original cart problem

'''
import gym
env = gym.make('CartPole-v0')
env.reset()
for _ in range(1000):
    env.render()
    env.step(env.action_space.sample()) # take a random action

'''

# cart problem with agent-environment loop
# loop has timesteps in which an agent chooses an action, enviorment return observation
# and the reward.
# in addition, reset() that returns observations, and done flag.
'''
import gym
env = gym.make('CartPole-v0')
for i_episode in range(20):
    observation = env.reset()
    for t in range(100):
        env.render()
        print(observation)
        action = env.action_space.sample()
        observation, reward, done, info = env.step(action)
        if done:
            print("Episode finished after {} timesteps".format(t+1))
            break

'''

# My Version


def run_episode(env):
  observation = env.reset()
  totalreward = 0
  for _ in range(200):
    env.render()
    action = env.action_space.sample()
    observation, reward, done, info = env.step(action)
    totalreward += reward
    if done:
      break
  return totalreward
  
import gym
env = gym.make('CartPole-v0')
for i_episode in range(20):
  print(run_episode(env))

# these example comes with space ( enviornmental's space ).
# Every enviornment comes with action_space and observation_space
'''
import gym
env = gym.make('CartPole-v0')
#> Discrete(2) 
# fixed range of non-negative numbers.
print(env.action_space)
#> Box(4,) 
# space that represents n - dimensional box.
print(env.observation_space)
'''
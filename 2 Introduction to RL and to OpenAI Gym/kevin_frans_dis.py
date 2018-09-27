'''
  random linear inner product.
'''

import gym
import numpy as np

def run_episode(env, parameters):  
  observation = env.reset()
  totalreward = 0
  for _ in range(200):
    env.render();
    action = 0 if np.matmul(parameters,observation) < 0 else 1
    observation, reward, done, info = env.step(action)
    totalreward += reward
    if done:
      break
  return totalreward

# First test with random variables.
'''
env = gym.make('CartPole-v0')
parameters = np.random.rand(4) * 2 - 1  

for _ in range(100):
  print(run_episode(env, parameters));
'''

env = gym.make('CartPole-v0')
bestparams = None  
bestreward = 0  
paramlist = []
for x in range(20):  
  print("run %i"%x)
  parameters = np.random.rand(4) * 2 - 1
  reward = run_episode(env,parameters)
  if reward > bestreward:
    bestreward = reward
    bestparams = parameters
    print(bestreward, parameters)
    # considered solved if the agent lasts 200 timesteps
    if reward == 200:
      paramlist.append(parameters)

print(len(paramlist))
# 200 at this level
# [-0.22691797  0.56737417 -0.1013903   0.81034003]
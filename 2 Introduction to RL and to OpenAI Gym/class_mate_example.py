observation = env.reset()
weight = np.random.rand(4)
for _ in range(200):
	weighted_sum = np.dot(weight, observation)
	pi = 1 / (1 + np.exp(-weighted_sum))
	if pi > 0.5:
		action = 1
	else:
		action = 0
	
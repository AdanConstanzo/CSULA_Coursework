class World:
  def __init__(self, state, width = 5, height = 5):
    self.reward = 0
    self.state = state
    self.value_state = [(x, y) for x in range(width) for y in range(height)]

  _actions = { 'north': (-1, 0), 'south': (1, 0), 'east': (0, 1), 'west': (0, -1) }
  
  def move(self, action):

    if self.state == (0,1):
      self.reward += 10
      self.state = (4,1)
    elif self.state == (0,3):
      self.reward += 5
      self.state = (2,3)
    else:
      nextPosition = tuple(map(sum,zip(self.state, self._actions[action])))
      if nextPosition in self.value_state:
        self.state = nextPosition
      else:
        self.reward -= 1
    return (self.state, self.reward, action)

    


  

if __name__ == '__main__':
    grid_world = World((0,0))
    print(grid_world.move("west"))
    print(grid_world.move("north"))
    print(grid_world.move("east"))
    print(grid_world.move("south"))
    print(grid_world.move("west"))
    print(grid_world.move("west"))
    print(grid_world.move("east"))
    print(grid_world.move("east"))
    print(grid_world.move("east"))
    print(grid_world.move("east"))
    print(grid_world.move("east"))
    print(grid_world.move("south"))



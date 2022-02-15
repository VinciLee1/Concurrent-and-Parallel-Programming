from cProfile import label
from matplotlib import markers
import matplotlib.pyplot as plt
import json
import sys
from matplotlib.gridspec import GridSpec


fileName = sys.argv[1]
print(fileName)      
with open(fileName) as f:
    data = json.loads(json.load(f))
    for item in data:
        x = item["x"]
        y_ = item["y"]
        latency = item["latency"]
        y = [n / latency for n in y_]
        plt.plot(x, y, label=str(latency)+"ms")
        plt.legend(loc='upper left', bbox_to_anchor=(1.01, 1))
plt.savefig("task_size.png", bbox_inches="tight")

fig = plt.figure(figsize = (5, 25))
subplots = []

with open(fileName) as f:
    data = json.loads(json.load(f))
    axs = [fig.add_subplot(len(data), 1, i) for i in range(1, len(data) + 1)]
    for i, item in enumerate(data):
        x = item["x"]
        y_ = item["y"]
        latency = item["latency"]
        y = [n / latency for n in y_]
        axs[i].plot(x, y, label=str(latency)+"ms")
        axs[i].legend(loc='upper left', bbox_to_anchor=(1.01, 1))
    # adjust subplot sizes
    # gs = GridSpec(len(data), 1, height_ratios=[5, 2, 1])
    # for i in range(len(data)):
        # axs[i].set_position(gs[i].get_position(fig))

fig.savefig("task_size_sub.png", bbox_inches="tight")
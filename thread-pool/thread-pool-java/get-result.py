import math
import subprocess
import matplotlib.pyplot as plt

def run(parallelismMax, taskSize, latency, executorType):
    args = ["java", "ExecutorPerformanceTest.java", str(parallelismMax), str(taskSize), str(latency), str(executorType)]
    process = subprocess.Popen(args, stdout=subprocess.PIPE)
    result = process.communicate()[0]
    latencysResult = [int(r) for r in result.split()]
    return latencysResult

parallelismMax = 120
row = 6
col = 1
axes = [plt.subplot(row, col, i) for i in range(1, row * col + 1)]

gap = parallelismMax // (row * col)
for latency in range(1, 30, 4):
    res = run(parallelismMax, 50, latency, 0)
    parallelisms = [i for i in range(1, parallelismMax + 1)]
    for index in range(row * col):
        ax = axes[index]
        start = index * gap
        ax.plot(parallelisms[start:start + gap], res[start:start + gap], label=str(latency)+"ms")
        ax.set_title('consuming rate(messages/s)')
        ax.set_xlabel('parallelism')
        ax.set_ylabel('rate')
ax.legend(loc='upper left', bbox_to_anchor=(1.01, 1))
plt.savefig("result.png", bbox_inches="tight")
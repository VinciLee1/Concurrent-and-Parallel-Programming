import math
import subprocess
import sys
from matplotlib import markers
import matplotlib.pyplot as plt
from numpy import double
import json

def run(parallelismMax, taskSize, latency, executorType):
    args = ["java", "ExecutorPerformanceTest.java", str(parallelismMax), str(taskSize), str(latency), str(executorType)]
    process = subprocess.Popen(args, stdout=subprocess.PIPE)
    result = process.communicate()[0]
    latencysResult = double(result)
    return latencysResult

def progressbar(it, prefix="", size=60, file = sys.stdout):
    count = len(it)
    def show(j):
        x = int(size*j/count)
        file.write("%s[%s%s] %i/%i\r" % (prefix, "#"*x, "."*(size-x), j, count))
        file.flush()        
    show(0)
    for i, item in enumerate(it):
        yield item
        show(i+1)
    file.write("\n")
    file.flush()

def taskSize_x_latency_y():
    outPut = []
    for latency in range(1, 10, 1):
        rt = {"latency": latency}
        res = []
        taskSizes = [i for i in range(1, 100)]
        rt["x"] = taskSizes
        for taskSize in progressbar(taskSizes, "Computing: ", 40):
            res.append(run(taskSize, taskSize, latency, 2))
        rt["y"] = res
        outPut.append(rt)
    with open('cached_pool_result.json', 'w') as outfile:
        json.dump(json.dumps(outPut, indent = 2), outfile)

taskSize_x_latency_y()

def parallelism_x_latency_y():
    parallelismMax = 60
    row = 6
    col = 1
    axes = [plt.subplot(row, col, i) for i in range(1, row * col + 1)]

    gap = parallelismMax // (row * col)
    for latency in range(10, 13, 1):
        res = run(parallelismMax, 50, latency, 1)
        res = [p / latency for p in res]
        parallelisms = [i for i in range(1, parallelismMax + 1)]
        for index in range(row * col):
            ax = axes[index]
            start = index * gap
            ax.plot(parallelisms[start:start + gap], res[start:start + gap], marker = 'o', label=str(latency)+"ms")
            ax.set_title('consuming rate(messages/s)')
            ax.set_xlabel('parallelism')
            ax.set_ylabel('rate')
    ax.legend(loc='upper left', bbox_to_anchor=(1.01, 1))
    plt.savefig("result.png", bbox_inches="tight")


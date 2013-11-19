import time
import shutil
import subprocess
experiment_name = time.time()
shutil.mkdir(experiment_name)
PROB_PUT = .1
N_ITER = 1000000
CACHE_SIZE = 100
SIZE_MEAN = 10000000
SIZE_VARIANCE = 1000000
KEY_VARINCE = 5
VERBOSE_GC_FLAG = "-verbose:gc"
BENCH_PROG = "com.scottandrewcarr.caching.BenchMark"
BASE_DIR="/home/carr27/caching"
    

def get_class_path():
    cp = []
    IDEA_OUTPUT = BASE_DIR + "/out"
    COMMONS_LOGGING = BASE_DIR + "/libs/commons-logging-1.1.3.jar"
    JCS = BASE_DIR + "/libs/jcs-1.3.jar"
    cp.append(IDEA_OUTPUT)
    cp.append(COMMONS_LOGGING)
    cp.append(JCS)
    cp_string = ""
    for i in range(len(cp)):
        if i == 0:
            cp_string = cp[i]
        else:
            cp_string = cp_string + ":"+ cp[i]
    return cp_string
    
def get_heap_size_flag(heap_size_mb):
    return "-Xmx{0}M"

def get_gc_type_param(useConcMarkSweep):
    if useConcMarkSeep:
        return "-XX:+UseConcMarkSweepGC"
    else:
        return ""
        
bench_params = [PROB_PUT, N_ITER, CACHE_SIZE, SIZE_MEAN, SIZE_VARIANCE, \
                KEY_VARIANCE]
vm_params = []

all_params = vm_params.append(bench_params)
subprocess.call(BENCH_PROG, all_params)




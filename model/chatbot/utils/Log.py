import logging

logger = logging.getLogger()
logger.setLevel(logging.DEBUG)

formatter = logging.Formatter(u'%(asctime)s [%(levelname)8s] %(message)s')

# FileHandler
file_handler = logging.FileHandler('../../output.log')
file_handler.setFormatter(formatter)

logger.addHandler(file_handler)
